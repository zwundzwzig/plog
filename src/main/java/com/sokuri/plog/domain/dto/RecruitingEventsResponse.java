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
public class RecruitingEventsResponse {
  private UUID id;
  private String title;
  private LocalDate beginEvent;
  private LocalDate finishEvent;
  private String organizer;
  private List<String> images;
  private String location;
  private int dues;

}
