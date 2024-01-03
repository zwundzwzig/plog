package com.sokuri.plog.domain.repository.feed;

import com.sokuri.plog.domain.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, UUID> {
  Optional<Hashtag> findByName(String names);
}
