package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.EventSummaryResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.EventService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
