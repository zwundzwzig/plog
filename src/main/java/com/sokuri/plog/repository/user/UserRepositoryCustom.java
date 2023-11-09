package com.sokuri.plog.repository.user;

import com.sokuri.plog.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
  Optional<User> findByNickname(String nickname);
}
