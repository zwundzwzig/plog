package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.trash.SearchNearbyTrashCanResponse;
import com.sokuri.plog.service.TrashService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public ResponseEntity<Void> setPublicTrashCan() {
    trashService.setPublicTrashCan();
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "근처에 있는 쓰레기통 제공 위치 검색", description = "사용자 현재 위치 위도, 경도 파라미터로 받으면 요청한 반경 내 모든 쓰레기통 위치")
  @GetMapping("/nearby")
  public ResponseEntity<List<SearchNearbyTrashCanResponse>> getAllRouteWithCurrentLocation(
          @RequestParam(value = "latitude") Double latitude, @RequestParam(value = "longitude") Double longitude, @RequestParam(value = "km") int range
  ) {
    return ResponseEntity.ok(trashService.getNearbyTrashCanList(longitude, latitude, range));
  }
}
