package com.sokuri.plog.controller;

import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("")
    public ResponseEntity<?> getEventList(@RequestParam(value = "status", defaultValue = "RECRUITING", required = false) String status) {
        return ResponseEntity.ok()
                .body(eventService.getEventList(RecruitStatus.fromString(status)));
    }

}
