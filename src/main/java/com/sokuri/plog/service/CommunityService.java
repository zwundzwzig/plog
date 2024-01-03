package com.sokuri.plog.service;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter;
import com.sokuri.plog.global.dto.community.CommunityDetailResponse;
import com.sokuri.plog.global.dto.community.CommunitySummaryResponse;
import com.sokuri.plog.global.dto.CoordinateDto;
import com.sokuri.plog.global.dto.community.CreateCommunityRequest;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityService {
  private final CommunityRepository communityRepository;
  private final ImageService imageService;
  private final UserService userService;
  private final RoadNameAddressToCoordinateConverter roadNameAddressToCoordinateConverter;

  @Transactional(readOnly = true)
  public void checkDuplicatedCommunity(String title) {
    communityRepository.findByTitle(title).ifPresent(name -> {
      throw new DataIntegrityViolationException("이미 존재하는 크루 이름이에요!");
    });
  }

  @Transactional(readOnly = true)
  public Community findById(String id) {
    return communityRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("해당 ID 값을 가진 크루 모집은 존재하지 않아요."));
  }

  public List<CommunitySummaryResponse> getCommunityList(RecruitStatus status) {
    return communityRepository.findCommunitiesByStatusIs(status)
            .stream()
            .map(Community::toSummaryResponse)
            .collect(Collectors.toList());
  }

  public List<CommunitySummaryResponse> getAllCommunityList() {
    return communityRepository.findAll()
            .stream()
            .map(Community::toSummaryResponse)
            .collect(Collectors.toList());
  }

  public CommunityDetailResponse getCommunityDetail(String id) {
    Community community = findById(id);

    CommunityDetailResponse response = community.toDetailResponse();
    CoordinateDto coordinate = roadNameAddressToCoordinateConverter.convertAddressToCoordinate(community.getLocation())
            .block();

    response.setVenue(
            coordinate.getBuildingName().isEmpty()
            ? community.getLocation() : coordinate.getBuildingName());

    coordinate = new CoordinateDto(coordinate.getLat(), coordinate.getLng());
    response.setPosition(coordinate);
    return response;
  }

  @Transactional
  public Map<String, String> create(CreateCommunityRequest request) {
    checkDuplicatedCommunity(request.getTitle());

    Community community = request.toEntity();
    community.setOrganizer(userService.findById(request.getUser()));

    Community response = communityRepository.save(community);

    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.saveAllCommunityImage(files, response));

    return new HashMap<>() {{
      put("id", response.getId().toString());
    }};
  }

  @Transactional
  public Map<String, String> update(CreateCommunityRequest request, String id) {
    Community targetCommunity = findById(id);

    Optional.ofNullable(request.getDescription()).ifPresent(targetCommunity::setDescription);
    Optional.ofNullable(request.getLink()).ifPresent(targetCommunity::setLink);
    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.updateCommunityImage(files, targetCommunity));

    return new HashMap<>() {{
      put("id", id);
    }};
  }

  @Transactional
  public void delete(String id) {
    Community targetCommunity = findById(id);
    communityRepository.delete(targetCommunity);
  }
}
