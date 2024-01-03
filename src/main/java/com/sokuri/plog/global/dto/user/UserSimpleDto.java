package com.sokuri.plog.global.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserSimpleDto {
  private UUID id;
  private String nickname;
  private String avatar;
}