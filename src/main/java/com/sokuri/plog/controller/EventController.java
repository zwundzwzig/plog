package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.RecruitingEventsResponse;
import com.sokuri.plog.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("")
    public ResponseEntity<List<RecruitingEventsResponse>> getRecruitingEvent() {
        List<RecruitingEventsResponse> response = eventService.getRecruitingEvent();
        return ResponseEntity.ok(response);
    }

}
