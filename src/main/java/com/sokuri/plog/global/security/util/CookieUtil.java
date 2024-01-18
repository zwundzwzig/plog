package com.sokuri.plog.global.security.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.util.SerializationUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;
import java.util.Optional;

public class CookieUtil {
  public static final String COOKIE_TOKEN_REFRESH = "refresh-token";

  public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (name.equals(cookie.getName()))
          return Optional.of(cookie);
      }
    }
    return Optional.empty();
  }

  public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(maxAge);

    response.addCookie(cookie);
  }

  public static ResponseCookie addResponseCookie(String name, String value, int maxAge) {
    return ResponseCookie.from(name, value)
            .maxAge(maxAge)
            .sameSite("None")
            .secure(true)
            .httpOnly(true)
            .path("/")
            .build();
  }

  public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (name.equals(cookie.getName())) {
          cookie.setValue("");
          cookie.setPath("/");
          cookie.setMaxAge(0);
          response.addCookie(cookie);
        }
      }
    }
  }

  public static String serialize(Object obj) {
    return Base64.getUrlEncoder()
            .encodeToString(SerializationUtils.serialize(obj));
  }

  public static <T> T deserialize(Cookie cookie, Class<T> cls) throws IOException, ClassNotFoundException {
    return cls.cast(deserializeWithObjectInputStream(Base64.getUrlDecoder().decode(cookie.getValue())));
  }

  private static Object deserializeWithObjectInputStream(byte[] bytes) throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
         ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
      return objectInputStream.readObject();
    }
  }

}

