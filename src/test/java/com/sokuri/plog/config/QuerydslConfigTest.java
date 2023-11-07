package com.sokuri.plog.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sokuri.plog.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import static com.sokuri.plog.domain.QUser.user;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QuerydslConfigTest {
  @Autowired
  EntityManager entityManager;

  private final String NICKNAME = "testDsl";
  private final String MAIL = "test@dsl.com";

  @Test
  @DisplayName("쿼리 dsl 세팅 테스트")
  void contextDslLoads() {
    User testUser = User.builder()
            .nickname(NICKNAME)
            .email(MAIL)
            .build();
    entityManager.persist(testUser);

    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

    User queryUser = queryFactory
            .selectFrom(user)
            .where(user.nickname.eq(NICKNAME))
            .fetchOne();

    assertThat(queryUser).isEqualTo(testUser);
    assertThat(queryUser.getId()).isEqualTo(testUser.getId());
  }
}
