package com.sokuri.plog.controller;

import com.sokuri.plog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1.0/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "엑세스 토큰 갱신")
    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(HttpServletRequest req) {
        HttpHeaders headers = authService.refresh(req);
        return ResponseEntity.ok().headers(headers).build();
    }
}
