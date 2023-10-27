package com.sokuri.plog.repository;

import com.sokuri.plog.domain.relations.user.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID>, LikeRepositoryCustom {
}
