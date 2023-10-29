package com.sokuri.plog.service;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter;
import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.domain.dto.event.CreateEventsRequest;
import com.sokuri.plog.domain.dto.event.EventDetailResponse;
import com.sokuri.plog.domain.dto.event.EventSummaryResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ImageService imageService;
    private final RoadNameAddressToCoordinateConverter roadNameAddressToCoordinateConverter;

  @Transactional(readOnly = true)
  public void checkDuplicatedEvent(String title) {
    eventRepository.findByTitle(title).ifPresent(name -> {
      throw new DataIntegrityViolationException("이미 존재하는 행사 이름이에요!");
    });
  }

  @Transactional(readOnly = true)
  public Event findById(String id) {
    return eventRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("해당 ID 값을 가진 행사는 존재하지 않아요."));
  }

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
    Event event = findById(id);

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

  @Transactional
  public Map<String, String> create(CreateEventsRequest request) {
    checkDuplicatedEvent(request.getTitle());
    Event response = eventRepository.save(request.toEntity());

    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.saveAllEventImage(files, response));

    return new HashMap<>() {{
      put("id", response.getId().toString());
    }};
  }

  @Transactional
  public Map<String, String> update(CreateEventsRequest request, String id) {
    Event targetEvent = findById(id);

    Optional.ofNullable(request.getDescription()).ifPresent(targetEvent::setDescription);
    Optional.ofNullable(request.getWebsite()).ifPresent(targetEvent::setWebsite);
    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.updateEventImage(files, targetEvent));

    return new HashMap<>() {{
      put("id", id);
    }};
  }

  @Transactional
  public void delete(String id) {
    Event targetEvent = findById(id);
    eventRepository.delete(targetEvent);
  }
}
