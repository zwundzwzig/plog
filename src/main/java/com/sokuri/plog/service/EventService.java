package com.sokuri.plog.service;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter;
import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.domain.dto.EventDetailResponse;
import com.sokuri.plog.domain.dto.EventSummaryResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final RoadNameAddressToCoordinateConverter roadNameAddressToCoordinateConverter;

    public List<EventSummaryResponse> getEventList(RecruitStatus status) {
      return eventRepository.findEventsByStatusIsAndRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(
                    status, LocalDateTime.now(), LocalDateTime.now()
                )
                .stream()
                .map(Event::toSummaryResponse)
                .collect(Collectors.toList());
    }

  public List<EventSummaryResponse> getAllEventList() {
    return eventRepository.findEventsByRecruitPeriodBeginRecruitIsBeforeAndRecruitPeriodFinishRecruitIsAfter(
                    LocalDateTime.now(), LocalDateTime.now()
            )
            .stream()
            .map(Event::toSummaryResponse)
            .collect(Collectors.toList());
  }

  public EventDetailResponse getEventDetail(String id) {
    Event event = eventRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("해당 ID 값을 가진 행사는 존재하지 않아요."));

    EventDetailResponse response = event.toDetailResponse();
    CoordinateDto coordinate = roadNameAddressToCoordinateConverter.convertAddressToCoordinate(event.getLocation())
            .block();

    response.setVenue(
            coordinate.getBuildingName().isEmpty()
            ? event.getLocation() : coordinate.getBuildingName() + " (" + event.getLocation() + ")");

    coordinate = new CoordinateDto(coordinate.getLat(), coordinate.getLng());
    response.setPosition(coordinate);

    return response;
  }
}
