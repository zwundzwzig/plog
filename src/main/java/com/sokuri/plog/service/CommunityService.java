package com.sokuri.plog.service;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter;
import com.sokuri.plog.domain.dto.CommunityDetailResponse;
import com.sokuri.plog.domain.dto.CommunitySummaryResponse;
import com.sokuri.plog.domain.dto.CoordinateDto;
import com.sokuri.plog.domain.dto.CreateCommunityRequest;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityService {
  private final CommunityRepository communityRepository;
  private final ImageService imageService;
  private final RoadNameAddressToCoordinateConverter roadNameAddressToCoordinateConverter;

  public void checkDuplicatedCommunity(String title) {
    communityRepository.findByTitle(title).ifPresent(name -> {
      throw new DataIntegrityViolationException("이미 존재하는 크루 이름이에요!");
    });
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
    Community community = communityRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("해당 ID 값을 가진 크루 모집은 존재하지 않아요."));

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

  public Map<String, String> create(CreateCommunityRequest request) {
    Community community = request.toEntity();
    checkDuplicatedCommunity(community.getTitle());

    Community response = communityRepository.save(community);

    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.saveAllCommunityImage(files, response));

    return new HashMap<>() {{
      put("id", response.getId().toString());
    }};
  }
}
