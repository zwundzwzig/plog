package com.sokuri.plog.controller;

import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/feed")
public class FeedController {

  private final FeedService feedService;

  @GetMapping("")
  public ResponseEntity<?> getFeedList(@RequestParam(value = "status", defaultValue = "PUBLIC", required = false) String status) {
    return ResponseEntity.ok()
            .body(feedService.getFeedList(AccessStatus.setDefaultValue(status)));
  }

}
