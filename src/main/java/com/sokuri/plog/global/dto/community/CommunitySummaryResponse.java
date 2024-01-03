package com.sokuri.plog.global.dto.community;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CommunitySummaryResponse {
  private UUID id;
  private String title;
  private LocalDateTime createdAt;
  private String location;
  private List<String> images;
  private int currentParticipants;
  private int maxParticipants;
}
