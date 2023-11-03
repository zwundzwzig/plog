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

import javax.persistence.NoResultException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  private final ImageService imageService;

  public void setTempPassword(String to, String authNum) {
  }

  public User getUserInfo(String id) {
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
            .email(user.getEmail())
            .nickname(user.getNickname())
            .profileImage(user.getProfileImage())
            .build();
  }
}
