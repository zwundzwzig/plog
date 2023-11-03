package com.sokuri.plog.domain.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponse {
  private String nickname;
  private String email;
  private String profileImage;
}
