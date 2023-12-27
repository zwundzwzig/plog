package com.sokuri.plog.service;

import com.sokuri.plog.domain.dto.user.TokenResponse;
import com.sokuri.plog.exception.BaseException;
import com.sokuri.plog.utils.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sokuri.plog.exception.CustomErrorCode.NOT_EXIST_REFRESH_TOKEN;

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
    String redisRefreshToken = redisTemplate.opsForValue().get(refreshToken);

    if(!redisRefreshToken.equals(refreshToken))
      throw new BaseException(NOT_EXIST_REFRESH_TOKEN);

    TokenResponse newAccessToken = jwtProvider.generateToken(authentication.getName());

    return jwtProvider.setTokenHeaders(newAccessToken);
  }
}
