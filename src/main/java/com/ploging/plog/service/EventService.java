package com.ploging.plog.service;

import com.ploging.plog.domain.Event;
import com.ploging.plog.domain.dto.EventForPlogingTabDto;
import com.ploging.plog.domain.eums.RecruitStatus;
import com.ploging.plog.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventForPlogingTabDto> getRecruitingEvent() {
        return eventRepository.findEventsByStatusIsAndBeginRecruitIsBeforeAndFinishRecruitIsAfter(RecruitStatus.RECRUITING, LocalDateTime.now(), LocalDateTime.now())
                .stream()
                .map(EventForPlogingTabDto::new)
                .collect(Collectors.toList());
    }
}
