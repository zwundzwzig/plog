package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.service.TrashService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "쓰레기통 정보", description = "서울시 공공 데이터 기반으로 쓰레기 위치 정보 제공")
@RestController
@RequestMapping("/v1.0/trash")
@RequiredArgsConstructor
public class TrashController {
  private final TrashService trashService;

  @Operation(summary = "쓰레기통 세팅")
  @GetMapping("")
  public ResponseEntity<List<CoordinateDto>> setPublicTrashCan() {
    List<CoordinateDto> response = trashService.setPublicTrashCan();
    return ResponseEntity.ok(response);
  }
}
