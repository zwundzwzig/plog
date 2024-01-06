package com.sokuri.plog.global.security.handler;

import com.sokuri.plog.global.exception.CustomErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException {
    CustomErrorCode exception = request.getAttribute("exception") == null
            ? CustomErrorCode.UNKNOWN_ERROR
            : (CustomErrorCode) request.getAttribute("exception");
    setResponse(request, response, exception, authException);
  }

  private void setResponse(HttpServletRequest request, HttpServletResponse response, CustomErrorCode errorCode, AuthenticationException authException) throws IOException {
    response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json;charset=UTF-8");

    JSONObject responseJson = new JSONObject();
    responseJson.put("message", errorCode.getMessage());
    responseJson.put("code", errorCode.getCode());
    responseJson.put("path", request.getContextPath() + request.getServletPath());
    responseJson.put("auth_message", authException.getMessage());

    response.getWriter().print(responseJson);
  }
}
