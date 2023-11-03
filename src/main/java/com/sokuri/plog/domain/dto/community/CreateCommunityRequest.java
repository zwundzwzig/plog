package com.sokuri.plog.domain.dto.community;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.embed.FestivalPeriod;
import com.sokuri.plog.domain.embed.RecruitPeriod;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateCommunityRequest {
  private List<MultipartFile> images;
  private String title;
  private String link;
  private LocalDateTime beginRecruit;
  private LocalDateTime finishRecruit;
  private String location;
  private int maxParticipants;
  private LocalDateTime beginEvent;
  private LocalDateTime finishEvent;
  private String description;
  private String user;

  public Community toEntity() {
    return Community.builder()
            .title(title)
            .link(link)
            .recruitPeriod(new RecruitPeriod(beginRecruit, finishRecruit))
            .location(location)
            .status(RecruitStatus.DEFAULT)
            .maxParticipants(maxParticipants)
            .eventPeriod(new FestivalPeriod(beginEvent, finishRecruit))
            .description(description)
            .build();
  }
}
