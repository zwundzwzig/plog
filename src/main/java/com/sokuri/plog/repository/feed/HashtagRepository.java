package com.sokuri.plog.repository.feed;

import com.sokuri.plog.domain.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, UUID> {
  Optional<Hashtag> findByName(String names);
}
