package com.ploging.plog.controller;

import com.ploging.plog.controller.RequestCouplerMail;
import com.ploging.plog.domain.MailMessage;
import com.ploging.plog.dto.MailRequestDto;
import com.ploging.plog.dto.MailResponseDto;
import com.ploging.plog.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MailController implements RequestCouplerMail {

    private final MailService mailService;

//    @Override
//    public ResponseEntity<MailDto> sendMail(@RequestParam String address, @RequestParam String title, @RequestParam String content, @RequestParam String sender) {
//        MailDto dto = new MailDto();
//        dto.setAddress(address);
//        dto.setTitle(title);
//        dto.setContent(content);
//        dto.setContent(sender);
//
//        mailService.sendMail(dto);
//
//        return ResponseEntity.ok(dto);
//    }

    @Override
    public String index() {
        return "index";
    }

    // 임시 비밀번호 발급
    @Override
    public ResponseEntity sendPasswordMail(@RequestBody MailRequestDto dto) {
        MailMessage emailMessage = MailMessage.builder()
                .to(dto.getMail())
                .subject("[So-Cool-It] 임시 비밀번호 발급")
                .build();

        mailService.sendMail(emailMessage, "password");

        return ResponseEntity.ok().build();
    }

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @Override
    public ResponseEntity sendJoinMail(MailRequestDto dto) {
        MailMessage emailMessage = MailMessage.builder()
                .to(dto.getMail())
                .subject("[So-Cool-It] 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = mailService.sendMail(emailMessage, "email");

        MailResponseDto mailResponseDto = new MailResponseDto();
        mailResponseDto.setCode(code);

        return ResponseEntity.ok(mailResponseDto);
    }

}
