package com.sokuri.plog.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunitySummaryResponse {
  private UUID id;
  private String title;
  private LocalDateTime createdAt;
  private String location;
  private List<String> images;
  private int currentParticipants;
  private int maxParticipants;
}
