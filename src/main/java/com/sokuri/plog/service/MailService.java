package com.sokuri.plog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

//  private final JavaMailSender javaMailSender;
//  private final SpringTemplateEngine templateEngine;
//  private final UserService userService;
//
//  @Override
//  public String sendMail(MailMessage mailMessage, String type) {
//
//    String authNum = createCode();
//
//    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//    if (type.equals("password")) userService.setTempPassword(mailMessage.getTo(), authNum);
//
//    try {
//      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
//      mimeMessageHelper.setTo(mailMessage.getTo()); // 메일 수신자
//      mimeMessageHelper.setSubject(mailMessage.getSubject()); // 메일 제목
//      mimeMessageHelper.setText(setContext(authNum, type), true); // 메일 본문 내용, HTML 여부
//      javaMailSender.send(mimeMessage);
//
//      log.info("Success");
//
//      return authNum;
//
//    } catch (MessagingException e) {
//      log.info("fail");
//      throw new RuntimeException(e);
//    }
//  }
//
//  // 인증번호 및 임시 비밀번호 생성 메서드
//  public String createCode() {
//    Random random = new Random();
//    StringBuffer key = new StringBuffer();
//
//    for (int i = 0; i < 8; i++) {
//      int index = random.nextInt(4);
//
//      switch (index) {
//        case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
//        case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
//        default: key.append(random.nextInt(9));
//      }
//    }
//    return key.toString();
//  }
//
//  // thymeleaf를 통한 html 적용
//  public String setContext(String code, String type) {
//
//    Context context = new Context();
//    context.setVariable("code", code);
//
//    return templateEngine.process(type, context);
//
//  }

}
