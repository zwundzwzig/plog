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

  @Override
  public Optional<Like> exist(UUID userId, UUID feedId) {
    return Optional.ofNullable(jpaQueryFactory
            .selectFrom(like)
            .where(like.user.id.eq(userId),
                    like.feed.id.eq(feedId))
            .fetchOne());
  }

  @Override
  public int findLikeCountByFeedId(UUID feedId) {
    return jpaQueryFactory
            .selectFrom(like)
            .where(like.feed.id.eq(feedId))
            .fetch().size();
  }
}
