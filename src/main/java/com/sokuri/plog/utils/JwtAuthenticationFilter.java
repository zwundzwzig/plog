package com.sokuri.plog.utils;

import com.sokuri.plog.exception.CustomErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    String accessToken = resolveToken(request);
    String requestURI = request.getRequestURI();

    if (StringUtils.hasText(accessToken) && !requestURI.contains("/auth/")) {
      try{
        Jwts.parserBuilder()
                .setSigningKey(jwtUtil.getKey())
                .build()
                .parseClaimsJws(accessToken);

        Authentication authentication = jwtUtil.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      catch (ExpiredJwtException e){
        request.setAttribute("exception", CustomErrorCode.EXPIRED_TOKEN.getCode());
      } catch (MalformedJwtException e){
        request.setAttribute("exception", CustomErrorCode.WRONG_TYPE_TOKEN.getCode());
      }
    }

    filterChain.doFilter(request, response);
  }

  private String resolveToken(HttpServletRequest request) {
    String accessToken = request.getHeader(JwtProperty.HEADER_STRING);
    if (StringUtils.hasText(accessToken) && accessToken.startsWith(JwtProperty.TOKEN_PREFIX)) {
      return accessToken.substring(7);
    }
    return null;
  }
}
