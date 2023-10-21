package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.CommunitySummaryResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/community")
public class CommunityController {

  private final CommunityService communityService;

  @GetMapping("")
  public ResponseEntity<?> getCommunityList(@RequestParam(value = "status", required = false) String status) {
    List<CommunitySummaryResponse> response = EnumUtils.isValidEnumIgnoreCase(RecruitStatus.class, status)
            ? communityService.getCommunityList(RecruitStatus.valueOf(status.toUpperCase()))
            : communityService.getAllCommunityList();
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEventDetail(@PathVariable String id) {
    return ResponseEntity.ok().body(communityService.getCommunityDetail(id));
  }
}
