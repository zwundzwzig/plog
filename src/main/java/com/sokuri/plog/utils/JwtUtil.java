package com.sokuri.plog.utils;

import com.sokuri.plog.domain.dto.user.TokenResponse;
import com.sokuri.plog.domain.eums.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@Getter
public class JwtUtil {
  private final Key key;

  public JwtUtil() {
    byte[] keyBytes = Decoders.BASE64.decode(JwtProperty.SECRET);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenResponse generateTokenResponse(String userSeq) {
    String accessToken = generateAccessToken(userSeq);
    String refreshToken = generateRefreshToken();

    return TokenResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  public String generateAccessToken(String userSeq) {
    long now = (new Date()).getTime();
    Date accessTokenExpiresIn = new Date(now + JwtProperty.ACCESS_TOKEN_EXPIRATION_TIME);
    return Jwts.builder()
            .setSubject(userSeq)
            .claim(JwtProperty.ROLE_KEY, Authority.ROLE_USER)
            .setExpiration(accessTokenExpiresIn)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
  }

  public String generateRefreshToken( ) {
    long now = (new Date()).getTime();
    return Jwts.builder()
            .setExpiration(new Date(now + JwtProperty.REFRESH_TOKEN_EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);

    if(claims.get(JwtProperty.ROLE_KEY) == null) {
      throw new RuntimeException("권한 정보가 없는 토큰 입니다.");
    }

    Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims.get(JwtProperty.ROLE_KEY).toString().split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());


    UserDetails principal = new User(claims.getSubject(),"", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(accessToken)
              .getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
