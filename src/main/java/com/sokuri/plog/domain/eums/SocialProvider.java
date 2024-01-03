package com.sokuri.plog.domain.eums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialProvider implements BaseEnumCode<String> {
  GOOGLE( "구글"), NAVER("네이버"), KAKAO( "카카오톡");

  private final String value;

  @Override
  public String getValue() {
    return this.value;
  }
}
