package com.sokuri.plog.global.dto.feed;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.eums.AccessStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class CreateFeedRequest {
  private List<MultipartFile> images;
  private String description;
  private String user;
  private Set<String> hashtags;

  public Feed toEntity() {
    return Feed.builder()
            .status(AccessStatus.DEFAULT)
            .description(description)
            .build();
  }
}
