package com.sokuri.plog.domain.repository.feed;

import com.sokuri.plog.domain.entity.Feed;
import com.sokuri.plog.domain.eums.AccessStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedRepository extends JpaRepository<Feed, UUID> {
  List<Feed> findAllByStatusIs(AccessStatus status);
}
