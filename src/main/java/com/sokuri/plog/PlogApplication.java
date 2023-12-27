package com.sokuri.plog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;

@EnableJpaRepositories(bootstrapMode = BootstrapMode.DEFERRED)
@EnableJpaAuditing
@SpringBootApplication
public class PlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(PlogApplication.class, args);
  }

}
