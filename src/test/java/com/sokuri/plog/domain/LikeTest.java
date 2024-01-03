package com.sokuri.plog.domain;

import com.sokuri.plog.domain.entity.Feed;
import com.sokuri.plog.domain.entity.User;
import com.sokuri.plog.domain.relations.user.Like;
import com.sokuri.plog.domain.repository.like.LikeRepository;
import com.sokuri.plog.service.LikeService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class LikeTest {
  @Autowired
  EntityManager entityManager;

  @Autowired
  LikeService likeService;
  @Autowired
  LikeRepository likeRepository;

  private User user;
  private Feed feed;
  private Like like;

  @BeforeEach
  void initData() {
    user = User.builder()
            .nickname("test")
            .email("test@test.com")
            .birthday(LocalDate.of(1994, 8, 2))
            .profileImage("image.com")
            .build();
    entityManager.persist(user);
    entityManager.flush();

    feed = Feed.builder()
            .description("test feed")
            .build();

    entityManager.persist(feed);
    entityManager.flush();
  }

  @Test
  @DisplayName("좋아요 여부 확인")
  void existLikeTest() {
    assertThat(likeRepository.exist(user.getId(), feed.getId())).isEmpty();
  }

  @Test
  @DisplayName("좋아요 기능 테스트")
  void pushLikeButtonTest() {
    likeService.pushLikeButton(user.getId().toString(), feed.getId().toString());
    assertThat(likeRepository.exist(user.getId(), feed.getId())).isNotNull();
  }

  @Test
  @DisplayName("좋아요 삭제 테스트")
  void pushLikeButtonForDeleteTest() {
    likeService.pushLikeButton(user.getId().toString(), feed.getId().toString());
    likeService.pushLikeButton(user.getId().toString(), feed.getId().toString());
    assertThat(likeRepository.exist(user.getId(), feed.getId())).isEmpty();
  }
}
