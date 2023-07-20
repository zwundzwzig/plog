package com.ploging.plog.service;

import com.ploging.plog.domain.dto.EventForPlogingTabDto;
import com.ploging.plog.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventForPlogingTabDto> getRecruitingEvent() {

        EventForPlogingTabDto eventForPlogingTabDto;

        eventRepository.findAll();

        return null;
    }
}
