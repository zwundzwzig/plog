package com.sokuri.plog.domain.eums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccessStatus implements BaseEnumCode<String> {
  PRIVATE("비공개"),
  PARTIAL("부분 공개"),
  PUBLIC("공개");

  private final String status;
  public static final AccessStatus DEFAULT = PUBLIC;

  @Override
  public String getValue() {
    return this.status;
  }

  public static AccessStatus fromString(String value) {
    try {
      return AccessStatus.valueOf(value);
    } catch (IllegalArgumentException e) {
      return DEFAULT;
    }
  }
}
