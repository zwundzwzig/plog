package com.sokuri.plog.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Value("${sokuri.address}")
  private String address;
  @Value("${sokuri.client}")
  private String client;
  @Value("${spring.mail.username}")
  private String email;

  @Bean
  public OpenAPI openApi(@Value("${springdoc.version}") String appVersion) {
    Server server = new Server()
            .url(address);

    Info info = new Info()
            .title("sokuri API")
            .version(appVersion)
            .description("소쿠리 웹 애플리케이션 API입니다.")
            .contact(new Contact()
                    .name("zwundzwzig")
                    .email(email)
                    .url(client)
            );

    return new OpenAPI()
            .components(new Components())
            .addServersItem(server)
            .info(info);
  }
}
