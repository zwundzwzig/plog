package com.sokuri.plog.service;

import com.sokuri.plog.domain.entity.Community;
import com.sokuri.plog.domain.entity.Event;
import com.sokuri.plog.domain.entity.Feed;
import com.sokuri.plog.domain.entity.Image;
import com.sokuri.plog.domain.relations.image.CommunityImage;
import com.sokuri.plog.domain.relations.image.EventImage;
import com.sokuri.plog.domain.relations.image.FeedImage;
import com.sokuri.plog.domain.repository.community.CommunityImageRepository;
import com.sokuri.plog.domain.repository.event.EventImageRepository;
import com.sokuri.plog.domain.repository.feed.FeedImageRepository;
import com.sokuri.plog.domain.repository.ImageRepository;
import com.sokuri.plog.global.utils.S3Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
  private final S3Upload s3Upload;
  private final ImageRepository imageRepository;
  private final CommunityImageRepository communityImageRepository;
  private final FeedImageRepository feedImageRepository;
  private final EventImageRepository eventImageRepository;
  private final static String COMMUNITY_DIRECTORY = "community";
  private final static String FEED_DIRECTORY = "feed";
  private final static String EVENT_DIRECTORY = "event";
  @Async
  @Transactional
  public Future<String> uploadS3Image(MultipartFile file, String directory) throws IOException {
    if (file.isEmpty()) return CompletableFuture.completedFuture("");
    return CompletableFuture.completedFuture(s3Upload.uploadToAws(file, directory));
  }

  @Transactional
  public Image saveImage(String url) {
    return imageRepository.save(Image.builder().url(url).build());
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void saveAllCommunityImage(List<MultipartFile> files, Community community) {
    List<CommunityImage> communityImageList = files.parallelStream()
            .map(communityImage -> setCommunityImageEntity(communityImage, community))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    communityImageRepository.saveAll(communityImageList);
  }

  private CommunityImage setCommunityImageEntity(MultipartFile file, Community community) {
    try {
      String url = uploadS3Image(file, COMMUNITY_DIRECTORY).toString();
      if (url.isEmpty()) return null;
      Image image = saveImage(url);

      return CommunityImage.builder()
              .community(community)
              .image(image)
              .build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void saveAllFeedImage(List<MultipartFile> files, Feed feed) {
    List<FeedImage> feedImageList = files.parallelStream()
            .map(feedImage -> setFeedImageEntity(feedImage, feed))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    feedImageRepository.saveAll(feedImageList);
  }

  private FeedImage setFeedImageEntity(MultipartFile file, Feed feed) {
    try {
      String url = uploadS3Image(file, FEED_DIRECTORY).toString();
      if (url.isEmpty()) return null;
      Image image = saveImage(url);

      return FeedImage.builder()
              .feed(feed)
              .image(image)
              .build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Transactional(propagation = Propagation.MANDATORY)
  public void saveAllEventImage(List<MultipartFile> files, Event event) {
    List<EventImage> eventImageList = files.parallelStream()
            .map(eventImage -> setEventImageEntity(eventImage, event))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    eventImageRepository.saveAll(eventImageList);
  }

  private EventImage setEventImageEntity(MultipartFile file, Event event) {
    try {
      String url = uploadS3Image(file, EVENT_DIRECTORY).toString();
      if (url.isEmpty()) return null;
      Image image = saveImage(url);

      return EventImage.builder()
              .event(event)
              .image(image)
              .build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Transactional
  public void updateCommunityImage(List<MultipartFile> files, Community community) {
    deleteAllByCommunity(community);
    saveAllCommunityImage(files, community);
  }

  private void deleteAllByCommunity(Community community) {
    communityImageRepository.deleteAllByCommunity(community);
  }

  @Transactional
  public void updateEventImage(List<MultipartFile> files, Event event) {
    deleteAllByEvent(event);
    saveAllEventImage(files, event);
  }

  private void deleteAllByEvent(Event event) {
    eventImageRepository.deleteAllByEvent(event);
  }

  @Transactional
  public void updateFeedImage(List<MultipartFile> files, Feed feed) {
    deleteAllByFeed(feed);
    saveAllFeedImage(files, feed);
  }

  private void deleteAllByFeed(Feed feed) {
    feedImageRepository.deleteAllByFeed(feed);
  }
}
