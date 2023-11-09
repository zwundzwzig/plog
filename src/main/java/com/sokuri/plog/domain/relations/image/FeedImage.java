package com.sokuri.plog.domain.relations.image;

import com.sokuri.plog.domain.Feed;
import com.sokuri.plog.domain.Image;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "feed_images")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedImage {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "feed_id")
  @NotNull
  private Feed feed;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  @NotNull
  private Image image;
}
