package com.sokuri.plog.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FeedsResponse {

  private String thumbnail;
  private String userId;
  private String avatar;
  private LocalDate createdAt;

}
