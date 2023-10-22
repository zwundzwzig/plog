package com.sokuri.plog.service;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.dto.FeedDetailResponse;
import com.sokuri.plog.domain.dto.FeedSummaryResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.repository.FeedRepository;
import com.sokuri.plog.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
  private final FeedRepository feedRepository;
  private final LikeRepository likeRepository;

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

  public FeedDetailResponse getFeedDetail(String id) {
    UUID uuid = UUID.fromString(id);
    Feed feed = feedRepository.findById(uuid)
            .orElseThrow(() -> new NoResultException("해당 ID 값을 가진 피드는 존재하지 않아요."));

    FeedDetailResponse response = feed.toDetailResponse();
    response.setLikes(likeRepository.findLikeCountByFeedId(uuid));
    return response;
  }
}
