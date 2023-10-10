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

    public List<EventForPlogingTabDto> getRecruitingEvent() {
        return eventRepository.findEventsByStatusIsAndRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(RecruitStatus.RECRUITING, LocalDateTime.now(), LocalDateTime.now())
                .stream()
                .map(EventForPlogingTabDto::new)
                .collect(Collectors.toList());
    }
}
