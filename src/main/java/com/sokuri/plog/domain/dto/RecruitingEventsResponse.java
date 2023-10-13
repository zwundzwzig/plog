package com.sokuri.plog.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecruitingEventsResponse {

  private String title;
  private LocalDateTime beginEvent;
  private LocalDateTime finishEvent;
  private String organizer;
  private String location;
  private int dues;

}
