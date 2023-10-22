package com.sokuri.plog.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CommunityDetailResponse {
  private String title;
  private LocalDate beginEvent;
  private LocalDate finishEvent;
  private UserSimpleDto organizer;
  private List<String> images;
  private String location;
  private String venue;
  private Timestamp dueDate;
  private String numOfPeople;
  private String content;
  private CoordinateDto position;
}
