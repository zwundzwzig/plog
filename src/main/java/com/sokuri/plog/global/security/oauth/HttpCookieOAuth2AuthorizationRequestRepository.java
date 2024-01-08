package com.sokuri.plog.global.security.oauth;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.sokuri.plog.global.security.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

  public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
  public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
  public final static String REFRESH_TOKEN = "refresh_token";
  private static final int COOKIE_EXPIRE_SECONDS = 180;

  @Override
  public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
    System.out.println("loadAuthorizationRequest :: " + request.getRequestURI());

    return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            .map(cookie -> {
              try {
                System.out.println(cookie.getAttributes());
                System.out.println(cookie.getName());
                return CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class);
              } catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("IllegalArgumentException :: " + e.getMessage());
                return null;
              }
            })
            .orElse(null);
  }

  @Override
  public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
                                       HttpServletResponse response) {
    System.out.println("saveAuthorizationRequest111 :: " + authorizationRequest.getAuthorizationUri());
    System.out.println("saveAuthorizationRequest111 :: " + authorizationRequest.getAuthorizationRequestUri());

    if (authorizationRequest == null) {
      CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
      CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
      CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
      return;
    }

    CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
    String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
    System.out.println("saveAuthorizationRequest :: " + redirectUriAfterLogin);
    if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
      CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS);
    }
  }

  @Override
  public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
    System.out.println("removeAuthorizationRequest :: " + request.getRequestURI());
    return this.loadAuthorizationRequest(request);
  }

  public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
    System.out.println("removeAuthorizationRequestCookies :: " + request.getRequestURI());
    CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
    CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
  }

}

