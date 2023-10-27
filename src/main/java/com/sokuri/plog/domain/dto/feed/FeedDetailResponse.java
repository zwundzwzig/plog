package com.sokuri.plog.domain.dto.feed;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class FeedDetailResponse {
  private String nickname;
  private String avatar;
  private String description;
  private List<String> hashtags;
  private int likes;
  private List<String> images;
  private LocalDateTime createdAt;
  private String timeSinceUpload;
}
