package com.sokuri.plog.domain.dto.event;

import com.sokuri.plog.domain.dto.CoordinateDto;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class EventDetailResponse {
  private String title;
  private LocalDate beginEvent;
  private LocalDate finishEvent;
  private String organizer;
  private List<String> images;
  private String location;
  private String venue;
  private int dues;
  private Timestamp dueDate;
  private String numOfPeople;
  private String website;
  private String content;
  private CoordinateDto position;
}
