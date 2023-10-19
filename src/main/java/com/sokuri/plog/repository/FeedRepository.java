package com.sokuri.plog.repository;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.eums.AccessStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedRepository extends JpaRepository<Feed, UUID> {
  List<Feed> findAllByStatusIs(AccessStatus status);
}
