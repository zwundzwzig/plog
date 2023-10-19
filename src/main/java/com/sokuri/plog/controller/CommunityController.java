package com.sokuri.plog.controller;

import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/community")
public class CommunityController {

  private final CommunityService communityService;

  @GetMapping("")
  public ResponseEntity<?> getCommunityList(@RequestParam(value = "status", defaultValue = "RECRUITING", required = false) String status) {
    return ResponseEntity.ok()
            .body(communityService.getCommunityList(RecruitStatus.fromString(status)));
  }

}
