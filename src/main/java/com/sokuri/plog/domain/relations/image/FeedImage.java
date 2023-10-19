package com.sokuri.plog.domain.relations.image;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.Image;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "feed_images")
@Getter
public class FeedImage {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name="uuid2", strategy = "uuid2")
  @Column(columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feed_id")
  private Feed feed;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image image;
}
