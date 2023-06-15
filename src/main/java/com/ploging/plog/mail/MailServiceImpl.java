package com.ploging.plog.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final JavaMailSender javaMailSender;

  @Override
  public void sendMail(MailDto dto) {
    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(dto.getAddress());
    simpleMailMessage.setSubject(dto.getTitle());
    simpleMailMessage.setText(dto.getContent());

    javaMailSender.send(simpleMailMessage);
  }

  public String createTempCode() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
  }

}
