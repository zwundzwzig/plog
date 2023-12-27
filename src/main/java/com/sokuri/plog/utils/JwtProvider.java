package com.sokuri.plog.utils;

import com.sokuri.plog.domain.dto.user.TokenResponse;
import com.sokuri.plog.domain.eums.Role;
import com.sokuri.plog.exception.BaseException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sokuri.plog.exception.CustomErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class JwtProvider {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.token.access-expiration-time}")
    private long accessExpirationTime;

    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long refreshExpirationTime;

    @Value("${spring.jwt.header}")
    private String accessHeader;

    @Value("${spring.jwt.prefix}")
    private String prefix;

    private final String refresh = "_REFRESH";

    private Date now = new Date();

    public TokenResponse generateToken(String id) {
        Claims claims = Jwts.claims().setId(id);

        String accessToken = generateAccessToken(claims);
        String refreshToken = generateRefreshToken(claims);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateAccessToken(Claims claims){
        Date expireDate = new Date(now.getTime() + accessExpirationTime);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .claim("role", Role.USER)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        redisTemplate.opsForValue().set(
                claims.getId(),
                accessToken,
                accessExpirationTime,
                TimeUnit.MILLISECONDS
        );

        return accessToken;
    }

    public String generateRefreshToken(Claims claims){
        Date expireDate = new Date(now.getTime() + refreshExpirationTime);

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();

        redisTemplate.opsForValue().set(
                claims.getId(),
                refreshToken,
                refreshExpirationTime,
                TimeUnit.MILLISECONDS
        );

        return refreshToken;
    }

    /**
     * 토큰으로부터 클레임을 만들고, 이를 통해 User 객체 생성해 Authentication 객체 반환
     */
    public Authentication getAuthenticationByToken(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if(claims.get("role") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰 입니다.");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getId(),"", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public String resolveToken(HttpServletRequest req) {
        try {
            String bearerToken = req.getHeader(accessHeader);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(prefix)) {
                return bearerToken.substring(7);
            }
        } catch (ExpiredJwtException e) {
            log.error(NOT_EXIST_REFRESH_TOKEN.getMessage());
            throw new BaseException(NOT_EXIST_REFRESH_TOKEN);
        }
        return null;
    }

    public void validateToken(String token) {
        try{
            Claims claims = parseClaims(token);
            System.out.println("token :: " + token);
            System.out.println(claims);
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch(ExpiredJwtException e) {
            log.error(EXPIRED_TOKEN.getMessage());
            throw new AccessDeniedException("토큰이 만료되었습니다. 다시 로그인해 주세요.");
        } catch(MalformedJwtException e) {
            log.error(WRONG_TYPE_TOKEN.getMessage());
            throw new BaseException(WRONG_TYPE_TOKEN);
        } catch(JwtException e) {
            log.error(UNSUPPORTED_TOKEN.getMessage());
            throw new SignatureException(e.getMessage());
        }
    }

    public HttpHeaders setTokenHeaders(TokenResponse tokenResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(accessHeader, prefix + " " + tokenResponse.getAccessToken());
        headers.add(accessHeader + refresh, prefix + " " + tokenResponse.getRefreshToken());
        return headers;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }
}
