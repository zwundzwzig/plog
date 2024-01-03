package com.sokuri.plog.domain.repository.like;

import com.sokuri.plog.domain.relations.user.Like;

import java.util.Optional;
import java.util.UUID;

public interface LikeRepositoryCustom {
  Optional<Like> exist(UUID userId, UUID feedId);
  int findLikeCountByFeedId(UUID feedId);
}
