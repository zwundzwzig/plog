package com.ploging.plog.mail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RequestCouplerMail {

    @GetMapping("/sendMail")
    ResponseEntity<MailDto> sendMail(@RequestParam String address, @RequestParam String title, @RequestParam String content, @RequestParam String sender);
}
