package com.sokuri.plog.repository;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.User;
import com.sokuri.plog.domain.eums.Role;
import com.sokuri.plog.domain.eums.SocialProvider;
import com.sokuri.plog.repository.user.UserQueryRepository;
import com.sokuri.plog.repository.user.UserRepository;
import com.sokuri.plog.repository.user.UserRepositorySupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class BasicTest {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserQueryRepository userQueryRepository;
  @Autowired
  private UserRepositorySupport userRepositorySupport;

  @Test
  public void querydsl_Custom설정_기능_확인() {
    //given
    String nickname = "test_querydsl";
    String address = "test_querydsl@email.com";
    String password = "1234";
    LocalDate birthday = LocalDate.of(2023, 01, 01);
    String profileImage = "http://123.123.123.com";
    List<Feed> feeds = new ArrayList<>();
    userRepository.save(new User(UUID.randomUUID(), nickname, address, password, birthday, profileImage, feeds, Role.USER, SocialProvider.KAKAO));

    //when
    List<User> result = userRepositorySupport.findByNickname(nickname);

    //then
    assertAll(
            () -> assertEquals(result.size(), 1),
            () -> assertEquals(result.get(0).getNickname(), nickname)
    );
  }

  @Test
  public void querydsl만으로_기본_기능() {
    //given
    String nickname = "test_querydsl";
    String address = "test_querydsl@email.com";
    String password = "1234";
    LocalDate birthday = LocalDate.of(2023, 01, 01);
    String profileImage = "http://123.123.123.com";
    List<Feed> feeds = new ArrayList<>();
    userRepository.save(new User(UUID.randomUUID(), nickname, address, password, birthday, profileImage, feeds, Role.USER, SocialProvider.KAKAO));

    //when
    List<User> result = userQueryRepository.findByNickname(nickname);

    //then
    assertAll(
            () -> assertEquals(result.size(), 1),
            () -> assertEquals(result.get(0).getNickname(), nickname)
    );
  }
}

