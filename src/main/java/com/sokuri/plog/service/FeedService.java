package com.sokuri.plog.service;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.dto.FeedsResponse;
import com.sokuri.plog.domain.dto.RecruitingEventsResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.repository.EventRepository;
import com.sokuri.plog.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
  private final FeedRepository feedRepository;

  public List<FeedsResponse> getFeeds() {
    return feedRepository.findAll()
            .stream()
            .map(Feed::toResponse)
            .collect(Collectors.toList());
  }
}
