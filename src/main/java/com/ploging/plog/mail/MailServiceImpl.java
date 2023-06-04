package com.ploging.plog.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl {

  private final JavaMailSender javaMailSender;

  public void sendMail(MailContents contents, String type) {

    String authNumber = createTempCode();
    MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();

//    if (type.equals("password")) userService.SetTempPassword();

  }

  public String createTempCode() {
    return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
  }

}
