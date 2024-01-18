package com.sokuri.plog.service;

import com.sokuri.plog.global.dto.user.TokenResponse;
import com.sokuri.plog.global.exception.BaseException;
import com.sokuri.plog.global.security.util.CookieUtil;
import com.sokuri.plog.global.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.sokuri.plog.global.exception.CustomErrorCode.*;
import static com.sokuri.plog.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.COOKIE_EXPIRE_SECONDS;
import static com.sokuri.plog.global.security.util.CookieUtil.COOKIE_TOKEN_REFRESH;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

  private final JwtProvider jwtProvider;
  private final RedisTemplate<String, String> redisTemplate;

  @Transactional
  public ResponseCookie[] refresh(String refreshToken) {
    jwtProvider.verifyRefreshToken(refreshToken);

    Authentication authentication = jwtProvider.getAuthenticationByToken(refreshToken);
    String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName() + "_REFRESH");

    if (redisRefreshToken == null || !redisRefreshToken.equals(refreshToken))
      throw new BaseException(NOT_EXIST_TOKEN);

    TokenResponse newToken = jwtProvider.generateToken(
            authentication.getName(),
            authentication.getAuthorities().stream()
                    .findFirst()
                    .orElseThrow(IllegalAccessError::new)
                    .getAuthority()
    );

    return new ResponseCookie[] {
            CookieUtil.addResponseCookie(HttpHeaders.AUTHORIZATION, newToken.getAccessToken(), COOKIE_EXPIRE_SECONDS),
            CookieUtil.addResponseCookie(COOKIE_TOKEN_REFRESH, newToken.getRefreshToken(), COOKIE_EXPIRE_SECONDS)
    };
  }

}
