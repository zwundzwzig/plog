package com.sokuri.plog.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RecruitingCommunitiesResponse {
  private String title;
  private LocalDate createdAt;
  private String location;
  private String thumbnail;
  private int maxParticipants;
}
