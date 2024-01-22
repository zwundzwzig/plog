package com.sokuri.plog.domain.repository.feed;

import com.sokuri.plog.domain.entity.Feed;
import com.sokuri.plog.domain.eums.AccessStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedRepository extends JpaRepository<Feed, UUID> {
  Page<Feed> findAllByStatusIs(AccessStatus status, Pageable pageable);
}
