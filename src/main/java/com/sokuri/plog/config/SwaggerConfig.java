package com.sokuri.plog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi eventApi(){
    return GroupedOpenApi.builder()
            .group("event-api")
            .pathsToMatch("/v1.0/event/**")
            .build();
  }

  @Bean
  public GroupedOpenApi communityApi(){
    return GroupedOpenApi.builder()
            .group("community-api")
            .pathsToMatch("/v1.0/community/**")
            .build();
  }

  @Bean
  public GroupedOpenApi feedApi(){
    return GroupedOpenApi.builder()
            .group("feed-api")
            .pathsToMatch("/v1.0/feed/**")
            .build();
  }

  @Bean
  public GroupedOpenApi trashApi(){
    return GroupedOpenApi.builder()
            .group("trash-api")
            .pathsToMatch("/v1.0/trash/**")
            .build();
  }

  @Bean
  public GroupedOpenApi userApi(){
    return GroupedOpenApi.builder()
            .group("user-api")
            .pathsToMatch("/v1.0/user/**")
            .build();
  }

  @Bean
  public OpenAPI openApi(){
    return new OpenAPI()
            .info(new Info()
                    .title("sokuri API")
                    .description("API 명세서")
                    .version("0.0.1"));
  }
}
