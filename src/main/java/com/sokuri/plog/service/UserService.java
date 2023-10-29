package com.sokuri.plog.service;

import com.sokuri.plog.domain.User;
import com.sokuri.plog.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public void setTempPassword(String to, String authNum) {
  }

  public User getUserInfo(String id) {
    return userRepository.findById(UUID.fromString(id))
            .orElseThrow(() -> new NoResultException("존재하지 않는 회원이에요."));
  }

}
