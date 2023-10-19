package com.sokuri.plog.service;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.dto.FeedsResponse;
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

  public List<FeedsResponse> getFeedList(AccessStatus status) {
    return feedRepository.findAllByStatusIs(status)
            .stream()
            .map(Feed::toResponse)
            .collect(Collectors.toList());
  }
}
