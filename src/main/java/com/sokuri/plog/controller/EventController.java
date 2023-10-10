package com.sokuri.plog.controller;

import com.sokuri.plog.domain.dto.EventForPlogingTabDto;
import com.sokuri.plog.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/")
    public ResponseEntity<List<EventForPlogingTabDto>> getRecruitingEvent() {
        List<EventForPlogingTabDto> event = eventService.getRecruitingEvent();
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

}
