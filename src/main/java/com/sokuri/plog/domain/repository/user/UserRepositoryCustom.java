package com.sokuri.plog.domain.repository.user;

import com.sokuri.plog.domain.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {
  Optional<User> findByNickname(String nickname);
}
