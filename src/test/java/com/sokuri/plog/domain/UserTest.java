package com.sokuri.plog.domain;

import com.sokuri.plog.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class UserTest {
  private Validator validator;
  @Autowired
  private UserRepository userRepository;

  private User rightUser = new User(UUID.randomUUID(), "nickname1", "email@email.com", "", LocalDate.of(1994, 8, 2), "image.com", List.of());
  private User m2 = new User(UUID.randomUUID(), "", "email@email.com", "", LocalDate.of(1994, 8, 2), "image.com", List.of());
  private User m3 = new User(UUID.randomUUID(), null, "email@emailcom", "", LocalDate.of(1994, 8, 2), "image.com", List.of());
  private User m4 = new User(UUID.randomUUID(), "nickname3", "email@emailcom", "", LocalDate.of(1994, 8, 2), "image.com", List.of());
  private User m5 = new User(UUID.randomUUID(), "nickname3", "emailemail.com", "", LocalDate.of(1994, 8, 2), "image.com", List.of());
  private User m6 = new User(UUID.randomUUID(), "nickname3", "", "", LocalDate.of(1994, 8, 2), "image.com", List.of());
  private User m7 = new User(UUID.randomUUID(), "nickname3", null, "", LocalDate.of(1994, 8, 2), "image.com", List.of());

  @BeforeEach
  void setUp() {
    this.validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Test
  @DisplayName("유저 시퀀스 아이디 추출")
  void getId() {
    System.out.println(userRepository.findByNickname("hihi1").get(0).getId());
  }

  @Test
  void 유저_닉네임_유효성_검사() {
    List<User> users = List.of(rightUser, m2, m3);

    for (User user : users) {
      Set<ConstraintViolation<User>> violations = validator.validate(user);

      if (violations.isEmpty()) {
        System.out.println(user.getNickname() + "회원님은 유효한 회원입니다.");
      }
      else {
        System.out.print("유효하지 않은 회원입니다. :: ");
        for (ConstraintViolation<User> violation : violations) {
          System.out.println(violation.getMessage());
        }
      }
    }
  }
  @Test
  void 유저_이메일_유효성_검사() {
    List<User> users = List.of(rightUser, m4, m5, m6, m7);

    for (User user : users) {
      Set<ConstraintViolation<User>> violations = validator.validate(user);

      if (violations.isEmpty()) {
        System.out.println(user.getNickname() + " 회원의 이메일 " + user.getEmail() + "은/는 유효합니다.");
      }
      else {
        System.out.print(user.getNickname() + " 회원의 이메일이 " + user.getEmail() + "이기 때문에 유효하지 않은 회원입니다. :: ");
        for (ConstraintViolation<User> violation : violations) {
          System.out.println(violation.getMessage());
        }
      }
    }
  }
}
