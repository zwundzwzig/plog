package com.sokuri.plog.global.security.config;

import com.sokuri.plog.global.config.CorsConfig;
import com.sokuri.plog.global.security.oauth.CustomOAuth2UserService;
import com.sokuri.plog.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.sokuri.plog.global.security.handler.CustomAccessDeniedHandler;
import com.sokuri.plog.global.security.filter.CustomAuthenticationEntryPoint;
import com.sokuri.plog.global.security.handler.CustomAuthenticationFailureHandler;
import com.sokuri.plog.global.security.handler.CustomAuthenticationSuccessHandler;
import com.sokuri.plog.global.utils.JwtProvider;
import com.sokuri.plog.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import java.util.Set;

import static com.sokuri.plog.global.exception.CustomErrorCode.NOT_EXIST_TOKEN;
import static com.sokuri.plog.global.exception.CustomErrorCode.TOKEN_NOT_FOUND;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
  private final CorsConfig corsConfig;
  private final JwtProvider jwtProvider;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final UserService userService;
  private final RedisTemplate<String, String> redisTemplate;
  private static final String[] AUTH_WHITELIST = {
          "/error",
          "/*/oauth2/code/*",
          "/favicon.ico",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui/**",
          "/webjars/**",
          "/h2-console/**",
          "/v1.0/user/sign-**",
          "/v1.0/auth/**",
          "/v3/api-docs/**"
  };

  /*
   * By default, Spring OAuth2 uses
   * HttpSessionOAuth2AuthorizationRequestRepository to save
   * the authorization request. But, since our service is stateless, we can't save
   * it in
   * the session. We'll save the request in a Base64 encoded cookie instead.
   */
  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(CsrfConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
            .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin(FormLoginConfigurer::disable)
            .httpBasic(HttpBasicConfigurer::disable)
            .headers(headerConfig ->
                    headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .authorizeHttpRequests(authorizeRequest ->
                    authorizeRequest
                            .requestMatchers(AUTH_WHITELIST).permitAll()
                            .anyRequest().authenticated())

            .oauth2Login(oauth ->
                    oauth.authorizationEndpoint(endpoint ->
                            endpoint.authorizationRequestRepository(authorizationRequestRepository()))
                          .userInfoEndpoint(user -> user.userService(customOAuth2UserService))
                          .redirectionEndpoint(red -> red.baseUri("/*/oauth2/code/*"))
                          .successHandler(oAuth2AuthenticationSuccessHandler())
                          .failureHandler(oAuth2AuthenticationFailureHandler()))

            .logout(logoutConfigurer -> logoutConfigurer
                    .logoutUrl("/v1.0/auth/sign-out")
                    .addLogoutHandler(logoutHandler())
                    .logoutSuccessUrl("/")
                    .deleteCookies(HttpHeaders.AUTHORIZATION))

            .exceptionHandling(exceptionHandlingConfigurer ->
                    exceptionHandlingConfigurer
                            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
                            .accessDeniedHandler(new CustomAccessDeniedHandler()));

    http.apply(new JwtSecurityConfig(jwtProvider));

    return http.build();
  }

  @Bean
  public LogoutHandler logoutHandler() {
    return (request, response, authentication) -> {
      try {
          String token = jwtProvider.resolveToken(request);
          if (!StringUtils.hasText(token))
            request.setAttribute("exception", TOKEN_NOT_FOUND);

          if (!jwtProvider.validateToken(token))
            request.setAttribute("exception", NOT_EXIST_TOKEN);
          Authentication auth = jwtProvider.getAuthenticationByToken(token);

          Set<String> keysToDelete = redisTemplate.keys(auth.getName() + "*");
          redisTemplate.delete(keysToDelete);
      } catch (Exception e) {
        request.setAttribute("exception", NOT_EXIST_TOKEN);
        e.printStackTrace();
      }
    };
  }

  // 암호화에 필요한 PasswordEncoder Bean 등록
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /*
   * Oauth 인증 성공 핸들러
   * */
  @Bean
  public CustomAuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
    return new CustomAuthenticationSuccessHandler(userService, authorizationRequestRepository());
  }

  /*
   * Oauth 인증 실패 핸들러
   * */
  @Bean
  public CustomAuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
    return new CustomAuthenticationFailureHandler(authorizationRequestRepository());
  }

}
