package com.sokuri.plog.repository.feed;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.relations.hashtag.FeedHashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedHashtagRepository extends JpaRepository<FeedHashtag, UUID> {
  void deleteAllByFeed(Feed feed);
}
