package com.sokuri.plog.domain.eums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialProvider {

  GOOGLE("GOOGLE", "구글"),
  NAVER("NAVER", "네이버"),
  KAKAO("KAKAO", "카카오톡");

  private final String key;
  private final String title;

}
