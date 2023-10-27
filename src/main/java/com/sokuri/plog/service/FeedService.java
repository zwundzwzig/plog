package com.sokuri.plog.service;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.dto.feed.CreateFeedRequest;
import com.sokuri.plog.domain.dto.feed.FeedDetailResponse;
import com.sokuri.plog.domain.dto.feed.FeedSummaryResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.repository.FeedRepository;
import com.sokuri.plog.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
  private final FeedRepository feedRepository;
  private final LikeRepository likeRepository;
  private final UserService userService;
  private final ImageService imageService;
  private final HashtagService hashtagService;

  @Transactional(readOnly = true)
  public Feed findById(String id) {
    return feedRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("해당 ID 값을 가진 피드는 존재하지 않아요."));
  }

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

  @Transactional
  public Map<String, String> create(CreateFeedRequest request) {
    Feed feed = request.toEntity();
    feed.setUser(userService.getUserInfo(request.getUser()));

    Feed response = feedRepository.save(feed);

    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.saveAllFeedImage(files, response));
    Optional.ofNullable(request.getHashtags())
            .ifPresent(hashtag -> hashtagService.saveAllFeedHashtag(hashtag, response));

    return new HashMap<>() {{
      put("id", response.getId().toString());
    }};
  }

  @Transactional
  public Map<String, String> update(CreateFeedRequest request, String id) {
    Feed targetFeed = findById(id);

    Optional.ofNullable(request.getDescription()).ifPresent(targetFeed::setDescription);
    Optional.ofNullable(request.getHashtags())
            .ifPresent(files -> hashtagService.updateFeedHashtag(request.getHashtags(), targetFeed));
    Optional.ofNullable(request.getImages())
            .ifPresent(files -> imageService.updateFeedImage(files, targetFeed));

    return new HashMap<>() {{
      put("id", id);
    }};
  }

  @Transactional
  public void delete(String id) {
    Feed targetFeed = findById(id);
    feedRepository.delete(targetFeed);
  }
}
