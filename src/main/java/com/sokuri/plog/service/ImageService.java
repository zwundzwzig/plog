package com.sokuri.plog.service;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.Image;
import com.sokuri.plog.domain.relations.image.CommunityImage;
import com.sokuri.plog.repository.CommunityImageRepository;
import com.sokuri.plog.repository.ImageRepository;
import com.sokuri.plog.utils.S3Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
  private final S3Upload s3Upload;
  private final ImageRepository imageRepository;
  private final CommunityImageRepository communityImageRepository;
  private final String DIRECTORY_NAME = "community";
  @Async
  @Transactional
  public String uploadS3Image(MultipartFile file, String directory) throws IOException {
    return s3Upload.uploadToAws(file, directory);
  }

  @Async
  public void saveAllCommunityImage(List<MultipartFile> files, Community community) {
    List<CommunityImage> communityImageList = files.parallelStream()
            .map(communityImage -> setCommunityImageEntity(communityImage, community))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    List<CommunityImage> savedImages = communityImageRepository.saveAll(communityImageList);
    System.out.println(savedImages);
  }

  private CommunityImage setCommunityImageEntity(MultipartFile file, Community community) {
    try {
      String url = uploadS3Image(file, DIRECTORY_NAME);
      Image image = imageRepository.save(
              Image.builder()
                      .url(url)
                      .build());

      return CommunityImage.builder()
              .community(community)
              .image(image)
              .build();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
