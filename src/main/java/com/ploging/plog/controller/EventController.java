package com.ploging.plog.controller;

import com.ploging.plog.domain.dto.EventForPlogingTabDto;
import com.ploging.plog.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1.0/event")
public class EventController {

    private final EventService eventService;

    @GetMapping("/")
    @ResponseBody
    public List<EventForPlogingTabDto> getRecruitingEvent() {

        List<EventForPlogingTabDto> event = eventService.getRecruitingEvent();

        return event;
    }

}
