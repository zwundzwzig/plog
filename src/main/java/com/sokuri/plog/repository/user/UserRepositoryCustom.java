package com.sokuri.plog.repository.user;

import com.sokuri.plog.domain.User;

import java.util.List;

public interface UserRepositoryCustom {
  List<User> findByNickname(String nickname);
}
