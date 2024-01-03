package com.sokuri.plog.service;

import com.sokuri.plog.global.dto.user.TokenResponse;
import com.sokuri.plog.global.exception.BaseException;
import com.sokuri.plog.global.utils.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sokuri.plog.global.exception.CustomErrorCode.NOT_EXIST_TOKEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
  private final JwtProvider jwtProvider;
  private final RedisTemplate<String, String> redisTemplate;

  @Transactional
  public HttpHeaders refresh(HttpServletRequest req) {
    String refreshToken = jwtProvider.resolveToken(req);
    jwtProvider.validateToken(refreshToken);

    Authentication authentication = jwtProvider.getAuthenticationByToken(refreshToken);
    String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName() + "_REFRESH");

    if(redisRefreshToken == null || !redisRefreshToken.equals(refreshToken))
      throw new BaseException(NOT_EXIST_TOKEN);

    TokenResponse newAccessToken = jwtProvider.generateToken(
            authentication.getName(),
            authentication.getAuthorities().stream()
                    .findFirst()
                    .orElseThrow(IllegalAccessError::new) // 권한이 존재하지 않을 시 예외를 던진다.
                    .getAuthority()
    );

    return jwtProvider.setTokenHeaders(newAccessToken);
  }
}
