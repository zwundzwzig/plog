package com.sokuri.plog.service;

import com.sokuri.plog.domain.dto.EventForPlogingTabDto;
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
//        List<Event> events = eventRepository.findEventsByStatusIsAndRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(RecruitStatus.RECRUITING, LocalDateTime.now(), LocalDateTime.now())
//        List<Event> events = eventRepository.findEventsByRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(LocalDateTime.now(), LocalDateTime.now())
//        List<Event> events = eventRepository.findEventsByRecruitPeriodBeginRecruitIsBefore(LocalDateTime.now())
        List<Event> events = eventRepository.findEventsByRecruitPeriodBeginRecruitIsBefore(LocalDateTime.now())
                .stream()
                .collect(Collectors.toList());

        System.out.println("events " + events.size());
        System.out.println("events " + events.get(0).getId());
        System.out.println("eventRepository.findAll()1 " + eventRepository.findEventsByRecruitPeriodBeginRecruitIsBefore(LocalDateTime.now()).get(0));
        System.out.println("eventRepository.findAll()2 " + eventRepository.findEventsByRecruitPeriodBeginRecruitIsBefore(LocalDateTime.now()).get(1));
        return null;
    }
}
