package com.sokuri.plog.controller;

import com.sokuri.plog.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/relation")
@Tag(name = "좋아요, 팔로우 정보", description = "좋아요, 팔로우 API")
public class LikeController {
  private final LikeService likeService;

  @Operation(summary = "좋아요 버튼")
  @PostMapping("/like")
  public ResponseEntity<Void> pushLikeButton(@RequestParam(value = "user") String userId, @RequestParam("feed") String feedId) {
    likeService.pushLikeButton(userId, feedId);
    return ResponseEntity.noContent().build();
  }
}
