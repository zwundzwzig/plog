package com.sokuri.plog.repository;

import java.util.UUID;

public interface LikeRepositoryCustom {
  int findLikeCountByFeedId(UUID feedId);
}
