package com.sokuri.plog.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class CorsConfig {
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedHeaders(List.of("Authorization"));
    configuration.addAllowedOrigin("http://13.209.208.58:8080");
    configuration.addAllowedOrigin("http://13.209.208.58:3000");
    configuration.addAllowedOrigin("http://13.209.208.58:8080");

    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(
            Arrays.asList("Authorization", "Requestor-Type", "Content-Type",
                    "Access-Control-Allow-Headers", "Access-Control-Allow-Origin"));
    configuration.setExposedHeaders(
            Arrays.asList("X-Get-Header", "Access-Control-Allow-Methods", "Access-Control-Allow-Origin"));
    configuration.setAllowedMethods(Collections.singletonList("*"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}