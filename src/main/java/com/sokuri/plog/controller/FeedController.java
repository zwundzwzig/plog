package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.FeedSummaryResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/feed")
public class FeedController {

  private final FeedService feedService;

  @GetMapping("")
  public ResponseEntity<?> getFeedList(@RequestParam(value = "status", required = false) String status) {
    List<FeedSummaryResponse> response = EnumUtils.isValidEnumIgnoreCase(RecruitStatus.class, status)
            ? feedService.getFeedList(AccessStatus.valueOf(status.toUpperCase()))
            : feedService.getAllFeedList();
    return ResponseEntity.ok().body(response);
  }
}
