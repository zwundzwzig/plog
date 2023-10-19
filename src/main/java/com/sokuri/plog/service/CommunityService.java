package com.sokuri.plog.service;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.dto.RecruitingCommunitiesResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommunityService {
  private final CommunityRepository communityRepository;

  public List<RecruitingCommunitiesResponse> getCommunityList(RecruitStatus status) {
    return communityRepository.findCommunitiesByStatusIs(status)
            .stream()
            .map(Community::toResponse)
            .collect(Collectors.toList());
  }
}
