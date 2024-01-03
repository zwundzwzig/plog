package com.sokuri.plog.domain.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sokuri.plog.domain.entity.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sokuri.plog.domain.entity.QUser.user;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
  private final JPAQueryFactory queryFactory;

  public UserRepositorySupport(JPAQueryFactory queryFactory) {
    super(User.class);
    this.queryFactory = queryFactory;
  }

  public List<User> findByNickname(String name) {
    return queryFactory
            .selectFrom(user)
            .where(user.nickname.eq(name))
            .fetch();
  }
}
