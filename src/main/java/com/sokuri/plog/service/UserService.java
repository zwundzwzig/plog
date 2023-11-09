package com.sokuri.plog.service;

import com.sokuri.plog.domain.User;
import com.sokuri.plog.domain.dto.user.SignInRequest;
import com.sokuri.plog.domain.dto.user.SignInResponse;
import com.sokuri.plog.domain.dto.user.UserCheckResponse;
import com.sokuri.plog.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  private final ImageService imageService;

  public void setTempPassword(String to, String authNum) {
  }

  private void isValidUUID(String id) {
    try {
      UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("유효하지 않은 UUID입니다: " + id);
    }
  }

  @Transactional(readOnly = true)
  public User findById(String id) {
    isValidUUID(id);
    return userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("존재하지 않는 회원이에요."));
  }

  public UserCheckResponse isEmailExist(String email) {
    boolean isExists = userRepository.existsByEmail(email);
    return new UserCheckResponse(isExists);
  }

  public UserCheckResponse isNicknameExists(String nickname) {
    boolean isExists = userRepository.existsByNickname(nickname);
    return new UserCheckResponse(isExists);
  }

  @Transactional
  public User createUser(SignInRequest request) {
    User user = User.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .password(request.getPassword())
            .birthday(request.getBirthday())
            .profileImage(request.getImageUrl())
            .build();
    return userRepository.save(user);
  }

  @SneakyThrows
  @Transactional
  public SignInResponse signIn(SignInRequest request) {
    request.setImageUrl(imageService.uploadS3Image(request.getProfileImage(), "user"));
    User user = createUser(request);

    return SignInResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .profileImage(user.getProfileImage())
            .build();
  }

  public List<SignInResponse> getAllUserList() {
    return userRepository.findAll()
            .stream()
            .map(User::toSummaryResponse)
            .collect(Collectors.toList());
  }
}
