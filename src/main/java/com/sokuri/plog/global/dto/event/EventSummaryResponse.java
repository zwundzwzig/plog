package com.sokuri.plog.global.dto.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EventSummaryResponse {
  private UUID id;
  private String title;
  private LocalDateTime beginEvent;
  private LocalDateTime finishEvent;
  private LocalDateTime createdAt;
  private String organizer;
  private List<String> images;
  private String location;
  private int dues;
}
