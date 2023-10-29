package com.sokuri.plog.repository.like;

import java.util.UUID;

public interface LikeRepositoryCustom {
  int findLikeCountByFeedId(UUID feedId);
}
