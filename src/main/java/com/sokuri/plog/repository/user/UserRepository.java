package com.sokuri.plog.repository.user;

import com.sokuri.plog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, UserRepositoryCustom {
  Boolean existsByEmail(String email);
  Boolean existsByNickname(String nickname);
  Optional<User> findByEmail(String email);
}