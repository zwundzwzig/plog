package com.sokuri.plog.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecruitingCommunitiesResponse {
  private UUID id;
  private String title;
  private LocalDate createdAt;
  private String location;
  private List<String> images;
  private int currentParticipants;
  private int maxParticipants;
  private String timeSinceUpload;
}
