package com.sokuri.plog.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
public class CorsConfig {
  @Value("${sokuri.address}")
  private String address;
  @Value("${sokuri.client}")
  private String client;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.addAllowedOrigin(address);
    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.addAllowedOrigin(client);

    configuration.setAllowCredentials(true);
    configuration.setAllowedMethods(Collections.singletonList("*"));

    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}