package com.sokuri.plog.community;

import com.sokuri.plog.domain.dto.EventForPlogingTabDto;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
class CommunityServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    @Transactional
    void testGetRecruitingEvent() {

        List<EventForPlogingTabDto> dto = eventRepository.findEventsByStatusIsAndRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(RecruitStatus.RECRUITING, LocalDateTime.now(), LocalDateTime.now())
                .stream()
                .map(EventForPlogingTabDto::new)
                .collect(Collectors.toList());

        System.out.println(dto);
        System.out.println(dto.size());
        assertThat(dto).isNotEmpty();

    }

}