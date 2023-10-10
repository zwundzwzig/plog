package com.sokuri.plog.repository;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.eums.RecruitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findEventsByStatusIsAndRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(RecruitStatus status, LocalDateTime begin, LocalDateTime finish);

}
