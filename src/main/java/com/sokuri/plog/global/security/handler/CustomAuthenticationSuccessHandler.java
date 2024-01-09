package com.sokuri.plog.global.security.handler;

import com.sokuri.plog.domain.eums.SocialProvider;
import com.sokuri.plog.global.dto.user.SignInRequest;
import com.sokuri.plog.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.sokuri.plog.global.security.oauth.info.OAuth2UserInfo;
import com.sokuri.plog.global.security.oauth.info.OAuth2UserInfoFactory;
import com.sokuri.plog.global.security.util.CookieUtil;
import com.sokuri.plog.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.sokuri.plog.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.sokuri.plog.global.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REFRESH_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Value("${app.oauth2.authorizedRedirectUris}")
  private String redirectUri;
  @Value("${spring.jwt.token.refresh-expiration-time}")
  private long refreshExpirationTime;
  private final UserService userService;
  private final HttpCookieOAuth2AuthorizationRequestRepository authorizationRequestRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    String targetUrl = determineTargetUrl(request, response, authentication);

    if (response.isCommitted()) {
      log.error("Response has already been committed. Unable to redirect to " + targetUrl);
      return;
    }

    clearAuthenticationAttributes(request, response);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

  protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
            .map(Cookie::getValue);

    if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get()))
      throw new IllegalArgumentException("리다이렉트 uri 에러 입니다. ::" + redirectUri);

    String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

    OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
    SocialProvider providerType = SocialProvider.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

    OAuth2User user = ((OidcUser) authentication.getPrincipal());
    OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

    SignInRequest req = SignInRequest.builder().email(userInfo.getEmail()).build();
    HttpHeaders headers = userService.signIn(req);

    CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);

    CookieUtil.addCookie(response
            , HttpHeaders.AUTHORIZATION
            , userService.resolveToken(headers.get(HttpHeaders.AUTHORIZATION).get(0))
            , (int) refreshExpirationTime);

    return UriComponentsBuilder.fromUriString(targetUrl)
            .build()
            .encode(StandardCharsets.UTF_8)
            .toUriString();
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    super.clearAuthenticationAttributes(request);
    authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
  }

  private boolean isAuthorizedRedirectUri(String uri) {
    URI clientRedirectUri = URI.create(uri);
    URI authorizedUri = URI.create(redirectUri);

    return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
            && authorizedUri.getPort() == clientRedirectUri.getPort();
  }

}
