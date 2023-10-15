package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.RecruitingCommunitiesResponse;
import com.sokuri.plog.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/community")
public class CommunityController {

  private final CommunityService communityService;

  @GetMapping("")
  public ResponseEntity<List<RecruitingCommunitiesResponse>> getRecruitingEvent() {
    List<RecruitingCommunitiesResponse> response = communityService.getRecruitingCommunity();
    return ResponseEntity.ok().body(response);
  }

}
