package com.sokuri.plog.controller;

import com.sokuri.plog.global.dto.community.CommunityDetailResponse;
import com.sokuri.plog.global.dto.community.CommunitySummaryResponse;
import com.sokuri.plog.global.dto.community.CreateCommunityRequest;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.CommunityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Tag(name = "크루 정보", description = "크루 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class CommunityController {

  private final CommunityService communityService;

  @Operation(summary = "전체 크루 모집 목록 조회")
  @GetMapping("")
  public ResponseEntity<List<CommunitySummaryResponse>> getCommunityList(@RequestParam(value = "status", required = false) String status) {
    List<CommunitySummaryResponse> response = EnumUtils.isValidEnumIgnoreCase(RecruitStatus.class, status)
            ? communityService.getCommunityList(RecruitStatus.valueOf(status.toUpperCase()))
            : communityService.getAllCommunityList();
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "크루 모집 상세 조회")
  @GetMapping("/{id}")
  public ResponseEntity<CommunityDetailResponse> getCommunityDetail(@PathVariable(value = "id") String id) {
    return ResponseEntity.ok(communityService.getCommunityDetail(id));
  }

  @Operation(summary = "크루 모집 생성")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping("")
  public ResponseEntity<CreateCommunityRequest> createCommunity(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                         @RequestPart("request") CreateCommunityRequest request
  ) {
    request.setImages(files);
    Map<String, String> response = communityService.create(request);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.get("id"))
            .toUri();

    return ResponseEntity.created(location).build();
  }

  @Operation(summary = "크루 모집 수정")
  @PutMapping("/{id}")
  public ResponseEntity<CreateCommunityRequest> update(
          @PathVariable(value = "id") String id,
          @RequestPart(value = "files", required = false) List<MultipartFile> file,
          @RequestPart("request") CreateCommunityRequest request
  ) {
    request.setImages(file);
    Map<String, String> response = communityService.update(request, id);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.get("id"))
            .toUri();

    return ResponseEntity.ok().location(location).build();
  }

  @Operation(summary = "크루 모집 삭제")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable(value = "id") String id) {
    communityService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
