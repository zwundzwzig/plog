package com.sokuri.plog.config;

import com.sokuri.plog.exception.CustomAccessDeniedHandler;
import com.sokuri.plog.exception.CustomAuthenticationEntryPoint;
import com.sokuri.plog.utils.JwtAuthenticationFilter;
import com.sokuri.plog.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SpringSecurityConfig {
  private final CorsConfig corsConfig;
  private final JwtUtil jwtUtil;
  private static final String[] AUTH_WHITELIST = {
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui/**",
          "/webjars/**",
          "/auth/**",
          "/h2-console/**",
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
            .exceptionHandling(exceptionHandlingConfigurer ->
                    exceptionHandlingConfigurer
                            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                            .accessDeniedHandler(new CustomAccessDeniedHandler()))
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
            .addFilter(corsConfig.corsFilter());

    return http.build();
  }
}
