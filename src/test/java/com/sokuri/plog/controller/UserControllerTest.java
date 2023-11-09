package com.sokuri.plog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sokuri.plog.domain.User;
import com.sokuri.plog.domain.dto.user.SignInRequest;
import com.sokuri.plog.domain.dto.user.UserCheckRequest;
import com.sokuri.plog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  ObjectMapper mapper;
  @Autowired
  UserService userService;

  private static final String BASE_URL = "/v1.0/user";
  private static final String USER_EMAIL = "email@email.com";
  private static final String USER_NICKNAME = "nickname";
  private static final String USER_BIRTHDAY = "2022-01-01";
  private static final String USER_PASSWORD = "12341234";
  private static final String USER_PROFILEIMAGE = "https://profile-image.com";

  private static final String NOT_EXIST = "NOT_EXIST";

  private User saveUser;
  private User savedSignInUser;

  private SignInRequest signInRequest;
  private UserCheckRequest userInfoRequest;

  @BeforeEach
  void initData() {
    userInfoRequest = new UserCheckRequest(USER_EMAIL, USER_NICKNAME);
//    saveUser = userService.createUser(userInfoRequest);

    signInRequest = SignInRequest.builder()
            .email(USER_EMAIL)
            .nickname(USER_NICKNAME)
            .birthday(LocalDate.parse(USER_BIRTHDAY))
            .password(USER_PASSWORD)
            .imageUrl(USER_PROFILEIMAGE)
            .build();

    savedSignInUser = userService.createUser(signInRequest);
  }

  @Test
  @DisplayName("이메일 존재여부 확인")
  void isExistUserIdTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
                    .get(BASE_URL+"/checkMail/{email}", NOT_EXIST)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.isExists").value("false"));

    mockMvc.perform(MockMvcRequestBuilders
                    .get(BASE_URL+"/checkMail/{email}", USER_EMAIL)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.isExists").value("true"));
  }

  @Test
  @DisplayName("닉네임 존재여부 확인")
  void isExistNicknameTest() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
                    .get(BASE_URL+"/checkNickname/{nickname}", NOT_EXIST)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.isExists").value("false"));

    mockMvc.perform(MockMvcRequestBuilders
                    .get(BASE_URL+"/checkNickname/{nickname}", USER_NICKNAME)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.isExists").value("true"));
  }

  @Test
  @DisplayName("회원 가입")
  void signInTest() throws Exception {
    MockMultipartFile multipartFile1 = new MockMultipartFile("files", "test.jpeg", "multipart/form-data", "test file".getBytes(StandardCharsets.UTF_8) );
    MockMultipartFile request = new MockMultipartFile("request", "request", "application/json", mapper.writeValueAsString(signInRequest).getBytes(StandardCharsets.UTF_8));


    mockMvc.perform(MockMvcRequestBuilders.multipart(BASE_URL + "/signIn")
                    .file(multipartFile1)
                    .file(request)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
            .andExpect(status().is2xxSuccessful());
  }
}
