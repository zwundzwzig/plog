package com.sokuri.plog.domain.dto;

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
  private String email;
}