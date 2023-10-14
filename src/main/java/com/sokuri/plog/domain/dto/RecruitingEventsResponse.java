package com.sokuri.plog.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class RecruitingEventsResponse {

  private String title;
  private LocalDate beginEvent;
  private LocalDate finishEvent;
  private String organizer;
  private String thumbnail;
  private String location;
  private int dues;

}
