package com.ploging.plog.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailController implements RequestCouplerMail {

    private final MailService mailService;

    @Override
    public ResponseEntity<MailDto> sendMail(@RequestParam String address, @RequestParam String title, @RequestParam String content, @RequestParam String sender) {
        MailDto dto = new MailDto();
        dto.setAddress(address);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setContent(sender);

        mailService.sendMail(dto);

        return ResponseEntity.ok(dto);
    }
}
