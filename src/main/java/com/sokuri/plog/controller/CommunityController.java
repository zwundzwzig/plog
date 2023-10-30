package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.community.CommunitySummaryResponse;
import com.sokuri.plog.domain.dto.community.CreateCommunityRequest;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
  public ResponseEntity<?> getCommunityDetail(@PathVariable String id) {
    return ResponseEntity.ok().body(communityService.getCommunityDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<?> createCommunity(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                         @RequestPart("request") CreateCommunityRequest request
  ) {
    request.setImages(files);
    return ResponseEntity.ok().body(communityService.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
          @PathVariable String id,
          @RequestPart(value = "files", required = false) List<MultipartFile> file,
          @RequestPart("request") CreateCommunityRequest request
  ) {
    request.setImages(file);
    return ResponseEntity.ok().body(communityService.update(request, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    communityService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
