package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.feed.CreateFeedRequest;
import com.sokuri.plog.domain.dto.feed.FeedSummaryResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/feed")
public class FeedController {

  private final FeedService feedService;

  @GetMapping("")
  public ResponseEntity<?> getFeedList(@RequestParam(value = "status", required = false) String status) {
    List<FeedSummaryResponse> response = EnumUtils.isValidEnumIgnoreCase(AccessStatus.class, status)
            ? feedService.getFeedList(AccessStatus.valueOf(status.toUpperCase()))
            : feedService.getAllFeedList();
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getFeedDetail(@PathVariable String id) {
    return ResponseEntity.ok().body(feedService.getFeedDetail(id));
  }

  @PostMapping("")
  public ResponseEntity<?> createFeed(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                      @RequestPart("request") CreateFeedRequest request
  ) {
    request.setImages(files);
    Map<String, String> response = feedService.create(request);
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
          @PathVariable String id,
          @RequestPart(value = "files", required = false) List<MultipartFile> file,
          @RequestPart("request") CreateFeedRequest request
  ) {
    request.setImages(file);
    return ResponseEntity.ok().body(feedService.update(request, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    feedService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
