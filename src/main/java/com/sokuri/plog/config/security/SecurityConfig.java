package com.sokuri.plog.config.security;

import com.sokuri.plog.config.CorsConfig;
import com.sokuri.plog.exception.CustomAccessDeniedHandler;
import com.sokuri.plog.exception.CustomAuthenticationEntryPoint;
import com.sokuri.plog.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
  private final CorsConfig corsConfig;
  private final JwtProvider jwtProvider;
  private final RedisTemplate<String, String> redisTemplate;
  private static final String[] AUTH_WHITELIST = {
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui/**",
          "/webjars/**",
          "/h2-console/**",
          "/v1.0/user/sign-*",
          "/v1.0/auth/**",
          "/v3/api-docs/**"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(CsrfConfigurer::disable)
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
            .logout(logoutConfigurer -> logoutConfigurer
                    .logoutUrl("/v1.0/auth/sign-out")
                    .addLogoutHandler(logoutHandler())
                    .logoutSuccessUrl("/")
                    .deleteCookies("remember_me"))
            .exceptionHandling(exceptionHandlingConfigurer ->
                    exceptionHandlingConfigurer
                            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                            .accessDeniedHandler(new CustomAccessDeniedHandler()))
            .addFilter(corsConfig.corsFilter());

    http.apply(new JwtSecurityConfig(jwtProvider));

    return http.build();
  }

  @Bean
  public LogoutHandler logoutHandler() {
    return (request, response, authentication) -> {
      String token = jwtProvider.resolveToken(request);
      Authentication auth = jwtProvider.getAuthenticationByToken(token);
      redisTemplate.delete(auth.getName());
    };
  }

}
