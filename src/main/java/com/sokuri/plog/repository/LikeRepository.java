package com.sokuri.plog.repository;

import com.sokuri.plog.domain.relations.user.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {
}
