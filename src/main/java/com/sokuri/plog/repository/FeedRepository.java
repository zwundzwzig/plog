package com.sokuri.plog.repository;

import com.sokuri.plog.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedRepository extends JpaRepository<Feed, UUID> {
}
