package com.sokuri.plog.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException {

    Integer exception = (Integer) request.getAttribute("exception");

    if(exception == null) {
      setResponse(response, CustomErrorCode.UNKNOWN_ERROR);
    }
    //잘못된 타입의 토큰인 경우
    else if(exception.equals(CustomErrorCode.WRONG_TYPE_TOKEN.getCode())) {
      setResponse(response, CustomErrorCode.WRONG_TYPE_TOKEN);
    }
    //토큰 만료된 경우
    else if(exception.equals(CustomErrorCode.EXPIRED_TOKEN.getCode())) {
      setResponse(response, CustomErrorCode.EXPIRED_TOKEN);
    }
    //지원되지 않는 토큰인 경우
    else if(exception.equals(CustomErrorCode.UNSUPPORTED_TOKEN.getCode())) {
      setResponse(response, CustomErrorCode.UNSUPPORTED_TOKEN);
    }
    else {
      setResponse(response, CustomErrorCode.ACCESS_DENIED);
    }
  }

  private void setResponse(HttpServletResponse response, CustomErrorCode errorCode) throws IOException {
    response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    JSONObject json = new JSONObject();
    json.put("message", errorCode.getMessage());
    json.put("code", errorCode.getCode());

    response.getWriter().print(json.toString().getBytes(StandardCharsets.UTF_8));
  }
}

