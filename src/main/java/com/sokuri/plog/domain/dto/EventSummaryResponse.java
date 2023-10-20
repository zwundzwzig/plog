package com.sokuri.plog.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventSummaryResponse {
  private UUID id;
  private String title;
  private LocalDate beginEvent;
  private LocalDate finishEvent;
  private LocalDateTime createdAt;
  private String status;
  private String organizer;
  private List<String> images;
  private String location;
  private int dues;
}
