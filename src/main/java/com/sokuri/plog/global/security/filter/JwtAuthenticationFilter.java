package com.sokuri.plog.global.security.filter;

import com.sokuri.plog.global.utils.JwtProvider;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static com.sokuri.plog.global.exception.CustomErrorCode.*;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtProvider jwtProvider;

  private static final String[] AUTH_LIST = { "/v1.0/user/sign-up", "/v1.0/user/sign-in" };

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
          throws ServletException, IOException {
    String requestURI = request.getRequestURI();
    String accessToken = jwtProvider.resolveToken(request);

    if (!StringUtils.hasText(accessToken) && Arrays.stream(AUTH_LIST).noneMatch(requestURI::contains)) {
      request.setAttribute("exception", NOT_EXIST_TOKEN);
    }

    else if (StringUtils.hasText(accessToken) && Arrays.stream(AUTH_LIST).noneMatch(requestURI::contains)) {
      try{
        if (!jwtProvider.validateToken(accessToken))
          request.setAttribute("exception", NOT_EXIST_TOKEN);

        Authentication authentication = jwtProvider.getAuthenticationByToken(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (MalformedJwtException e){
        request.setAttribute("exception", WRONG_TYPE_TOKEN);
      } catch (JwtException e){
        request.setAttribute("exception", EXPIRED_TOKEN);
      } catch (RedisConnectionFailureException e) {
        SecurityContextHolder.clearContext();
        request.setAttribute("exception", REDIS_ERROR);
      }
    }

    filterChain.doFilter(request, response);
  }
}
