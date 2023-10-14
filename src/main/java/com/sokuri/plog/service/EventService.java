package com.sokuri.plog.service;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.dto.RecruitingEventsResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<RecruitingEventsResponse> getRecruitingEvent() {
        List<Event> events = eventRepository.findEventsByStatusIsAndRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(RecruitStatus.RECRUITING, LocalDateTime.now(), LocalDateTime.now())
                .stream()
                .collect(Collectors.toList());

        System.out.println("events " + events.size());
        System.out.println("events " + events.get(0));
        return null;
    }
}
