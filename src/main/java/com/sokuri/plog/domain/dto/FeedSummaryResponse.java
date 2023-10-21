package com.sokuri.plog.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeedSummaryResponse {
  private UUID id;
  private String nickname;
  private String avatar;
  private List<String> images;
  private LocalDate createdAt;
  private String timeSinceUpload;
}
