package com.ploging.plog.controller;

import com.ploging.plog.dto.MailRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mail-send")
public interface RequestCouplerMail {

//    @GetMapping("/sendMail")
//    ResponseEntity sendMail(@RequestParam String address, @RequestParam String title, @RequestParam String content, @RequestParam String sender);
    // 임시 비밀번호 발급

    @GetMapping("/")
    String index();

    @PostMapping("/password")
    ResponseEntity sendPasswordMail(@RequestBody MailRequestDto dto);

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    ResponseEntity sendJoinMail(MailRequestDto dto);

}
