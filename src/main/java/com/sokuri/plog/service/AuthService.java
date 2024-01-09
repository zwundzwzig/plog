package com.sokuri.plog.service;

import com.sokuri.plog.global.dto.user.TokenResponse;
import com.sokuri.plog.global.exception.BaseException;
import com.sokuri.plog.global.utils.JwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.DecodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.sokuri.plog.global.exception.CustomErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
  private final JwtProvider jwtProvider;
  private final RedisTemplate<String, String> redisTemplate;

  @Transactional
  public HttpHeaders refresh(String tokenInHeader) {
    String refreshToken = resolveTokenInHeader(tokenInHeader);
    verifyToken(refreshToken);

    Authentication authentication = jwtProvider.getAuthenticationByToken(refreshToken);
    String redisRefreshToken = redisTemplate.opsForValue().get(authentication.getName() + "_REFRESH");

    if (redisRefreshToken == null || !redisRefreshToken.equals(refreshToken))
      throw new BaseException(NOT_EXIST_TOKEN);

    TokenResponse newAccessToken = jwtProvider.generateToken(
            authentication.getName(),
            authentication.getAuthorities().stream()
                    .findFirst()
                    .orElseThrow(IllegalAccessError::new)
                    .getAuthority()
    );

    return jwtProvider.setTokenHeaders(newAccessToken);
  }

  private void verifyToken(String refreshToken) {
    try {
      Jwts.parser()
              .setSigningKey(jwtProvider.getSecretKey())
              .parseClaimsJws(refreshToken);
    } catch (DecodingException e) {
      throw new BaseException(UNSUPPORTED_TOKEN);
    } catch (ExpiredJwtException e) {
      throw new BaseException(EXPIRED_TOKEN);
    }
  }

  private String resolveTokenInHeader(String tokenInHeader) {

    if (StringUtils.hasText(tokenInHeader) && tokenInHeader.startsWith(jwtProvider.getPrefix())) {
      return tokenInHeader.substring(7);
    }
    else throw new BaseException(TOKEN_NOT_FOUND);
  }

}
