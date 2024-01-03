package com.sokuri.plog.global.dto.community;

import com.sokuri.plog.global.dto.CoordinateDto;
import com.sokuri.plog.global.dto.user.UserSimpleDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommunityDetailResponse {
  private String title;
  private LocalDateTime beginEvent;
  private LocalDateTime finishEvent;
  private UserSimpleDto organizer;
  private List<String> images;
  private String location;
  private String venue;
  private LocalDateTime dueDate;
  private String numOfPeople;
  private String content;
  private String link;
  private CoordinateDto position;
}
