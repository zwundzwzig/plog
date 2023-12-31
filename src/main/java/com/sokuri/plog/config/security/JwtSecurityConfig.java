package com.sokuri.plog.config.security;

import com.sokuri.plog.utils.JwtAuthenticationFilter;
import com.sokuri.plog.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Provider&Filter SecurityConfig에 적용
 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtProvider jwtProvider;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(jwtProvider);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
