package com.sokuri.plog.global.dto.user;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SignInResponse {
  private UUID id;
  private String nickname;
  private String email;
  private String profileImage;
}
