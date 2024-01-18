package com.sokuri.plog.controller;

import jakarta.servlet.http.HttpServletResponse;
import com.sokuri.plog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseCookie;

import static com.sokuri.plog.global.security.util.CookieUtil.COOKIE_TOKEN_REFRESH;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "엑세스 토큰 갱신")
    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue(COOKIE_TOKEN_REFRESH) final String refreshToken,
            final HttpServletResponse httpServletResponse
    ) {
        ResponseCookie[] newToken = authService.refresh(refreshToken);
        httpServletResponse.addHeader(SET_COOKIE, newToken[0].toString());
        httpServletResponse.addHeader(SET_COOKIE, newToken[1].toString());

        return ResponseEntity.status(CREATED).build();
    }
}
