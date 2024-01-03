package com.sokuri.plog.service;

import com.sokuri.plog.domain.entity.Feed;
import com.sokuri.plog.domain.entity.User;
import com.sokuri.plog.domain.relations.user.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.sokuri.plog.domain.repository.like.LikeRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
  private final LikeRepository likeRepository;
  private final FeedService feedService;
  private final UserService userService;

  public void pushLikeButton(String userId, String feedId) {
    Like like = new Like();
    Optional<Like> checkLike = likeRepository.exist(UUID.fromString(userId), UUID.fromString(feedId));
    if (!checkLike.isPresent()) {
      Feed feed = feedService.findById(feedId);
      User user = userService.findById(userId);
      like.setFeed(feed);
      like.setUser(user);
      likeRepository.save(like);
    }
    else {
      likeRepository.delete(checkLike.get());
    }
  }
}
