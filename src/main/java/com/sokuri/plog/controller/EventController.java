package com.sokuri.plog.controller;

import com.sokuri.plog.global.dto.event.EventDetailResponse;
import com.sokuri.plog.global.dto.event.EventSummaryResponse;
import com.sokuri.plog.global.dto.event.CreateEventsRequest;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.EventService;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/event")
@Tag(name = "행사 정보", description = "행사 API")
public class EventController {
    private final EventService eventService;

    @Operation(summary = "전체 행사 목록 조회")
    @GetMapping("")
    public ResponseEntity<List<EventSummaryResponse>> getEventList(@RequestParam(value = "status", required = false) String status) {
        System.out.println("EventController.getEventList :: " + status);
        List<EventSummaryResponse> response = EnumUtils.isValidEnumIgnoreCase(RecruitStatus.class, status)
                ? eventService.getEventList(RecruitStatus.valueOf(status.toUpperCase()))
                : eventService.getAllEventList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "행사 상세 조회")
    @GetMapping("/{id}")
    public ResponseEntity<EventDetailResponse> getEventDetail(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok(eventService.getEventDetail(id));
    }

    @Operation(summary = "행사 생성")
    @PostMapping("")
    public ResponseEntity<CreateEventsRequest> createEvent(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                             @RequestPart("request") CreateEventsRequest request
    ) {
        request.setImages(files);
        Map<String, String> response = eventService.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.get("id"))
                .toUri();

        return ResponseEntity.created(location).build();

    }

    @Operation(summary = "행사 수정")
    @PutMapping("/{id}")
    public ResponseEntity<CreateEventsRequest> update(
            @PathVariable String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> file,
            @RequestPart("request") CreateEventsRequest request
    ) {
        request.setImages(file);
        Map<String, String> response = eventService.update(request, id);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.get("id"))
                .toUri();

        return ResponseEntity.ok().location(location).build();
    }

    @Operation(summary = "행사 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") String id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
