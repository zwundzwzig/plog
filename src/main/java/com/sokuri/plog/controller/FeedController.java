package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.FeedsResponse;
import com.sokuri.plog.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/feed")
public class FeedController {

  private final FeedService feedService;

  @GetMapping("")
  public ResponseEntity<List<FeedsResponse>> getFeeds() {
    List<FeedsResponse> response = feedService.getFeeds();
    return ResponseEntity.ok().body(response);
  }

}
