package com.sokuri.plog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/v1.0/feed/**", config);
    source.registerCorsConfiguration("/v1.0/event/**", config);
    source.registerCorsConfiguration("/v1.0/community/**", config);
    source.registerCorsConfiguration("/v1.0/user/**", config);
    source.registerCorsConfiguration("/v1.0/trash/**", config);

    return new CorsFilter(source);
  }
}