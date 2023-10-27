package com.sokuri.plog.repository;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.relations.image.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedImageRepository extends JpaRepository<FeedImage, UUID> {
  void deleteAllByFeed(Feed feed);
}
