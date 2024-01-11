package com.sokuri.plog.domain;

import com.sokuri.plog.domain.eums.Role;
import com.sokuri.plog.global.dto.user.TokenResponse;
import com.sokuri.plog.global.utils.JwtProvider;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

import static com.sokuri.plog.global.exception.CustomErrorCode.EXPIRED_TOKEN;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtProviderTest {

  @Autowired
  private JwtProvider jwtProvider;
  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  private Authentication authentication;
  private MockHttpServletRequest request = new MockHttpServletRequest();

  private final String id = UUID.randomUUID().toString();
  private final String invalidTokenId = UUID.randomUUID().toString();
  private final String role = Role.USER.getKey();
  private TokenResponse token;
  private TokenResponse tokenNoAuthority;
  private TokenResponse tokenInvalidKey = new TokenResponse();
  private TokenResponse tokenExpired = new TokenResponse();

  @BeforeEach
  void initToken() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtProvider.getKey());
    byte[] invalidKeyBytes = Decoders.BASE64.decode(jwtProvider.getKey() + "Invalid");
    Key secretKey = Keys.hmacShaKeyFor(keyBytes);
    Key invalidSecretKey = Keys.hmacShaKeyFor(invalidKeyBytes);

    token = jwtProvider.generateToken(id, role);
    tokenNoAuthority = jwtProvider.generateToken(invalidTokenId, null);
    tokenInvalidKey.setAccessToken(Jwts.builder()
            .setExpiration(new Date(new Date().getTime() + 1000))
            .signWith(invalidSecretKey, SignatureAlgorithm.HS512).compact());
    tokenExpired.setAccessToken(Jwts.builder().setExpiration(new Date(new Date().getTime() - 1000))
            .signWith(secretKey, SignatureAlgorithm.HS512).compact());
  }

  @AfterEach
  void cleanToken() {
    Set<String> keysToDelete = redisTemplate.keys(id + "*");
    keysToDelete.addAll(List.of(invalidTokenId, invalidTokenId + "_REFRESH"));
    redisTemplate.delete(keysToDelete);
  }

  @Test
  @DisplayName("key init 테스트")
  void initTest() {
    assertNotNull(jwtProvider.getSecretKey());
    assertEquals(jwtProvider.getSecretKey().getClass(), SecretKeySpec.class);
    assertEquals(jwtProvider.getAccessHeader(), HttpHeaders.AUTHORIZATION);
  }

  @Test
  @DisplayName("액세스 토큰 테스트")
  void accessTokenTest() {
    String accessTokenInRedis = redisTemplate.opsForValue().get(id);

    assertNotNull(accessTokenInRedis);
    assertTrue(redisTemplate.expireAt(id, new Date()));
    assertEquals(token.getAccessToken(), accessTokenInRedis);
  }

  @Test
  @DisplayName("리프레쉬 토큰 테스트")
  void refreshTokenTest() {
    String refreshTokenInRedis = redisTemplate.opsForValue().get(id + "_REFRESH");

    assertNotNull(refreshTokenInRedis);
    assertTrue(redisTemplate.expireAt(id + "_REFRESH", new Date()));
    assertEquals(token.getRefreshToken(), refreshTokenInRedis);
  }

  @Test
  @DisplayName("Role 없을 때 에러 반환")
  void getAuthenticationByToken_throwAccessDeniedCase() {
    assertThrows(AccessDeniedException.class, ()
            -> jwtProvider.getAuthenticationByToken(tokenNoAuthority.getAccessToken()));
  }

  @Test
  @Transactional
  @DisplayName("Authentication 객체 제대로 가져오는 지 테스트")
  void getAuthenticationByToken_SuccessCase() {
    authentication = jwtProvider.getAuthenticationByToken(token.getAccessToken());

    assertNotNull(authentication);
    assertTrue(authentication.isAuthenticated());
  }

  @Test
  @DisplayName("UserDetails 객체 제대로 생성됐는 지 테스트")
  void GetAuthenticationByToken_getUserDetailsCase() {
    authentication = jwtProvider.getAuthenticationByToken(token.getAccessToken());
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    assertNotNull(userDetails);
    assertEquals(id, userDetails.getUsername());
  }

  @Test
  @DisplayName("Authorities 제대로 부여됐는 지 테스트")
  void getAuthenticationByToken_getAuthoritiesCase() {
    authentication = jwtProvider.getAuthenticationByToken(token.getAccessToken());
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    assertNotNull(authorities);
    assertTrue(authorities.contains(new SimpleGrantedAuthority(role)));
  }

  @Test
  @DisplayName("요청 헤더에서 있어야 되는 값이 null 경우 테스트")
  void resolveToken_NullCase() {
    String tokenWithNoAccessHeader = jwtProvider.resolveToken(request);
    request.addHeader(jwtProvider.getAccessHeader(), token.getAccessToken());
    String tokenWithNoPrefix = jwtProvider.resolveToken(request);

    assertNull(tokenWithNoAccessHeader);
    assertNull(tokenWithNoPrefix);
  }

  @Test
  @DisplayName("요청 헤더에서 제대로 토큰 뽑아내는 지 테스트")
  void resolveToken_SuccessCase() {
    StringBuilder sb = new StringBuilder();
    sb.append(jwtProvider.getPrefix());
    sb.append("\s");
    sb.append(token.getAccessToken());

    request.addHeader(HttpHeaders.AUTHORIZATION, sb);

    String resolvedToken = jwtProvider.resolveToken(request);

    assertNotNull(request.getHeader(HttpHeaders.AUTHORIZATION));
    assertNotNull(resolvedToken);
    assertEquals(token.getAccessToken(), resolvedToken);
  }

  @Test
  @DisplayName("잘못된 토큰이 헤더에 있을 경우")
  void resolveToken_InvalidTokenInHeaderTest() {
    request.addHeader(jwtProvider.getAccessHeader(), jwtProvider.getPrefix() + tokenNoAuthority.getAccessToken());

    String resolvedToken = jwtProvider.resolveToken(request);

    assertNotNull(resolvedToken);
    assertNotEquals(token.getAccessToken(), resolvedToken);
  }

  @Test
  @DisplayName("잘못된 키로 생성된 토큰 검증 테스트")
  void validateInvalidTokenTest() {
    assertThrows(SignatureException.class, ()
            -> jwtProvider.validateToken(tokenInvalidKey.getAccessToken()));
  }

  @Test
  @DisplayName("기간 만료된 토큰 검증 테스트")
  void validateExpiredTokenTest() {
    JwtException thrown = assertThrows(JwtException.class, ()
            -> jwtProvider.validateToken(tokenExpired.getAccessToken()));

    assertEquals(thrown.getMessage(), EXPIRED_TOKEN.getMessage());
  }

  @Test
  @DisplayName("토큰 null 검증 테스트")
  void validateNullTokenTest() {
    assertThrows(IllegalArgumentException.class, ()
            -> jwtProvider.validateToken(null));
  }

  @Test
  @DisplayName("토큰 검증 테스트")
  void validateTokenTest() {
    assertTrue(jwtProvider.validateToken(token.getAccessToken()));
    assertTrue(jwtProvider.validateToken(tokenNoAuthority.getAccessToken()));
  }

  @Test
  @DisplayName("토큰 형식 테스트")
  void validateMalFormedTokenTest() {
    assertThrows(MalformedJwtException.class, ()
            -> jwtProvider.validateToken(invalidTokenId));
  }

  @Test
  @DisplayName("헤더에 토큰 담기 테스트")
  void setTokenHeaders_SuccessTest() {
    HttpHeaders headers = jwtProvider.setTokenHeaders(token);

    assertNotNull(headers);
    assertNotNull(headers.get(HttpHeaders.AUTHORIZATION));
    assertNotNull(headers.get(HttpHeaders.AUTHORIZATION + jwtProvider.getRefresh()));
    assertNotEquals(headers.get(HttpHeaders.AUTHORIZATION), headers.get(HttpHeaders.AUTHORIZATION + jwtProvider.getRefresh()));
    assertEquals(headers.get(HttpHeaders.AUTHORIZATION).get(0), jwtProvider.getPrefix() + " " + token.getAccessToken());
    assertEquals(headers.get(HttpHeaders.AUTHORIZATION + jwtProvider.getRefresh()).get(0), jwtProvider.getPrefix() + " " + token.getRefreshToken());
  }

}
