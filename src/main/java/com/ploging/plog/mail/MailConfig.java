package com.ploging.plog.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

//  @Bean
//  public JavaMailSender javaMailSender() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//    mailSender.setHost("${spring.mail.host}");
//    mailSender.setPort(Integer.parseInt("${spring.mail.port}"));
//    mailSender.set("${spring.mail.host}");
//  }

}
