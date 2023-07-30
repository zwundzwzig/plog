package com.ploging.plog.repository;

import com.ploging.plog.domain.Event;
import com.ploging.plog.domain.dto.EventForPlogingTabDto;
import com.ploging.plog.domain.eums.RecruitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findEventsByStatusIsAndBeginRecruitIsBeforeAndFinishRecruitIsAfter(RecruitStatus status, LocalDateTime begin, LocalDateTime finish);

    List<Event> findEventsByBeginRecruitIsBefore(LocalDateTime now);
}
