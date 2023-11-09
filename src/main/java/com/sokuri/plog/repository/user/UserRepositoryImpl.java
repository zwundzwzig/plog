package com.sokuri.plog.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sokuri.plog.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.sokuri.plog.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<User> findByNickname(String nickname) {
    return Optional.ofNullable(queryFactory.selectFrom(user)
            .where(user.nickname.eq(nickname))
            .fetchOne());
  }
}
