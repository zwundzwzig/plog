package com.sokuri.plog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sokuri.plog.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sokuri.plog.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQueryRepository {
  private final JPAQueryFactory queryFactory;
  public List<User> findByNickname(String name) {
    return queryFactory
            .selectFrom(user)
            .where(user.nickname.eq(name))
            .fetch();
  }
}
