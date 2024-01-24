package com.sokuri.plog.controller;

import com.sokuri.plog.global.dto.SimpleDataResponse;
import com.sokuri.plog.global.dto.feed.CreateFeedRequest;
import com.sokuri.plog.global.dto.feed.FeedDetailResponse;
import com.sokuri.plog.global.dto.feed.FeedSummaryResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.service.FeedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
@Tag(name = "피드 정보", description = "피드 API")
public class FeedController {

  private final FeedService feedService;

  @Operation(summary = "전체 피드 목록 조회")
  @GetMapping("")
  public ResponseEntity<List<FeedSummaryResponse>> getFeedList(
          @RequestParam(value = "page", required = false) Integer page,
          @RequestParam(value = "limit", required = false) Integer limit,
          @RequestParam(value = "status", required = false) String status
  ) {
    page = page == null || page < 1 ? 1 : page;
    limit = limit == null || limit < 1 ? 10 : limit;

    List<FeedSummaryResponse> response = EnumUtils.isValidEnumIgnoreCase(AccessStatus.class, status)
            ? feedService.getFeedList(AccessStatus.valueOf(status.toUpperCase()), page, limit)
            : feedService.getAllFeedList(page, limit);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "피드 상세 조회")
  @GetMapping("/{id}")
  public ResponseEntity<FeedDetailResponse> getFeedDetail(@PathVariable(value = "id") String id) {
    return ResponseEntity.ok(feedService.getFeedDetail(id));
  }

  @Operation(summary = "피드 생성")
  @PostMapping("")
  public ResponseEntity<CreateFeedRequest> createFeed(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                      @RequestPart("request") CreateFeedRequest request
  ) {
    request.setImages(files);
    SimpleDataResponse response = feedService.create(request);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();

    return ResponseEntity.created(location).build();
  }

  @Operation(summary = "피드 수정")
  @PutMapping("/{id}")
  public ResponseEntity<CreateFeedRequest> update(
          @PathVariable String id,
          @RequestPart(value = "files", required = false) List<MultipartFile> file,
          @RequestPart("request") CreateFeedRequest request
  ) {
    request.setImages(file);
    SimpleDataResponse response = feedService.update(request, id);

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();

    return ResponseEntity.ok().location(location).build();
  }

  @Operation(summary = "피드 삭제")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable(value = "id") String id) {
    feedService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
