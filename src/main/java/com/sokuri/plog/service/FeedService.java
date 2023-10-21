package com.sokuri.plog.service;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter;
import com.sokuri.plog.domain.dto.FeedSummaryResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
  private final FeedRepository feedRepository;
  private final RoadNameAddressToCoordinateConverter roadNameAddressToCoordinateConverter;

  public List<FeedSummaryResponse> getFeedList(AccessStatus status) {
    return feedRepository.findAllByStatusIs(status)
            .stream()
            .map(Feed::toSummaryResponse)
            .collect(Collectors.toList());
  }

  public List<FeedSummaryResponse> getAllFeedList() {
    return feedRepository.findAll()
            .stream()
            .map(Feed::toSummaryResponse)
            .collect(Collectors.toList());
  }
}
