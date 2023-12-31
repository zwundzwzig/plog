package com.sokuri.plog.service;

import com.sokuri.plog.domain.User;
import com.sokuri.plog.domain.dto.user.*;
import com.sokuri.plog.domain.eums.Role;
import com.sokuri.plog.repository.user.UserRepository;
import com.sokuri.plog.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
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
  private final JwtProvider jwtProvider;

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

  @Transactional(readOnly = true)
  public User findByEmail(String email) {
    return userRepository.findByEmail(email)
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
  public User createUser(SignUpRequest request) {
    User user = User.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .password(request.getPassword())
            .birthday(request.getBirthday())
            .profileImage(request.getImageUrl())
            .role(Role.USER)
            .build();
    return userRepository.save(user);
  }

  @SneakyThrows
  @Transactional
  public SignInResponse signUp(SignUpRequest request) {
    if (isEmailExist(request.getEmail()).isExists())
      throw new DataIntegrityViolationException("이미 존재하는 이메일이에요!");

    if (isNicknameExists(request.getNickname()).isExists())
      throw new DataIntegrityViolationException("이미 존재하는 닉네임이에요!");

    if (request.getProfileImage() != null)
      request.setImageUrl(imageService.uploadS3Image(request.getProfileImage(), "user").toString());
    User user = createUser(request);

    return SignInResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .profileImage(user.getProfileImage())
            .build();
  }

  @Transactional
  public HttpHeaders signIn(SignInRequest request) {
    User targetUser = findByEmail(request.getEmail());
    TokenResponse token = jwtProvider.generateToken(targetUser.getId().toString(), targetUser.getRole().getKey());
    return jwtProvider.setTokenHeaders(token);
  }

  public List<SignInResponse> getAllUserList() {
    return userRepository.findAll()
            .stream()
            .map(User::toSummaryResponse)
            .collect(Collectors.toList());
  }
}
