package com.sokuri.plog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sokuri.plog.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sokuri.plog.domain.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<User> findByNickname(String nickname) {
    return queryFactory.selectFrom(user)
            .where(user.nickname.eq(nickname))
            .fetch();
  }
}
