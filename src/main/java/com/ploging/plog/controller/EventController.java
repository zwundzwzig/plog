package com.ploging.plog.controller;

import com.ploging.plog.domain.dto.EventForPlogingTabDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event/v1.0")
public class EventController {

    @GetMapping("/")
    public ResponseEntity<EventForPlogingTabDto> getRecruitingEvent() {
        return null;
    }

}
