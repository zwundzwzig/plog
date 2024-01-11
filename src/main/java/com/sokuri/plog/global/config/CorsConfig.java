package com.sokuri.plog.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CorsConfig {
  @Value("${sokuri.address}")
  private String address;
  @Value("${server.port}")
  private Integer port;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin(address + ":" + port);
    configuration.addAllowedOrigin("http://localhost:3000");

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