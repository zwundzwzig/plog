package com.sokuri.plog.service;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.Hashtag;
import com.sokuri.plog.domain.relations.hashtag.FeedHashtag;
import com.sokuri.plog.repository.feed.FeedHashtagRepository;
import com.sokuri.plog.repository.feed.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HashtagService {
  private final HashtagRepository hashtagRepository;
  private final FeedHashtagRepository feedHashtagRepository;

  @Transactional
  public Set<Hashtag> saveOrGetHashtags(Set<String> hashtagNames) {
    Set<Hashtag> hashtags = new HashSet<>();

    for (String hashtagName : hashtagNames) {
      Optional<Hashtag> existingHashtag = hashtagRepository.findByName(hashtagName);

      if (existingHashtag.isPresent()) {
        hashtags.add(existingHashtag.get());
      } else {
        Hashtag newHashtag = Hashtag.builder().name(hashtagName).build();
        hashtags.add(hashtagRepository.save(newHashtag));
      }
    }

    return hashtags;
  }

  public void saveAllFeedHashtag(Set<String> hashtags, Feed feed) {
    Set<Hashtag> hashtagSet = saveOrGetHashtags(hashtags);

    Set<FeedHashtag> feedHashtags = hashtagSet.stream()
            .map(hashtag ->
              FeedHashtag.builder()
                      .feed(feed)
                      .hashtag(hashtag)
                      .build()
            )
            .collect(Collectors.toSet());

    feedHashtags.forEach(feedHashtagRepository::save);
  }

  public void updateFeedHashtag(Set<String> hashtags, Feed feed) {
    deleteAllByFeed(feed);
    Optional.ofNullable(hashtags)
            .filter(hashtag -> !hashtag.isEmpty())
            .ifPresent(hashtag -> saveAllFeedHashtag(hashtag, feed));
  }

  private void deleteAllByFeed(Feed feed) {
    feedHashtagRepository.deleteAllByFeed(feed);
  }
}
