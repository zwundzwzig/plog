package com.sokuri.plog.repository.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sokuri.plog.domain.relations.user.Like;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static com.sokuri.plog.domain.relations.user.QLike.like;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  public Optional<Like> exist(UUID userId, UUID feedId) {
    Like pLike = jpaQueryFactory
            .selectFrom(like)
            .where(like.user.id.eq(userId),
                    like.feed.id.eq(feedId))
            .fetchFirst();

    return Optional.ofNullable(pLike);
  }

  @Override
  public int findLikeCountByFeedId(UUID feedId) {
    return (int) jpaQueryFactory
            .selectFrom(like)
            .where(like.feed.id.eq(feedId))
            .fetchCount();
  }
}
