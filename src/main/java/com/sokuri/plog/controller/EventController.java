package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.event.EventSummaryResponse;
import com.sokuri.plog.domain.dto.event.CreateEventsRequest;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/event")
public class EventController {
    private final EventService eventService;

    @GetMapping("")
    public ResponseEntity<?> getEventList(@RequestParam(value = "status", required = false) String status) {
        List<EventSummaryResponse> response = EnumUtils.isValidEnumIgnoreCase(RecruitStatus.class, status)
                ? eventService.getEventList(RecruitStatus.valueOf(status.toUpperCase()))
                : eventService.getAllEventList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(eventService.getEventDetail(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createEvent(@RequestPart(value = "files", required = false) List<MultipartFile> files,
                                             @RequestPart("request") CreateEventsRequest request
    ) {
        request.setImages(files);
        Map<String, String> response = eventService.create(request);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestPart(value = "files", required = false) List<MultipartFile> file,
            @RequestPart("request") CreateEventsRequest request
    ) {
        request.setImages(file);
        return ResponseEntity.ok().body(eventService.update(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
