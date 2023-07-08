package com.ploging.plog.domain.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class StringToUuid {
  public static UUID stringtoUUID(String id) {
    UUID uuid = null;
    try {
      // UUID 형식의 문자열인 경우
      uuid = UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      // UUID 형식이 아닌 경우, 숫자인 경우
      try {
        long longValue = Long.parseLong(id);
        uuid = UUID.nameUUIDFromBytes(String.valueOf(longValue).getBytes());
      } catch (NumberFormatException ex) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", ex);
      }
    }
    return uuid;
  }
}
