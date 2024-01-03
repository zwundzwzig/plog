package com.sokuri.plog.domain.relations.image;

import com.sokuri.plog.domain.entity.Community;
import com.sokuri.plog.domain.entity.Image;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "community_images")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityImage {
  @Id
  @GeneratedValue(generator = "community_image")
  @GenericGenerator(name = "community_image", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "community_id")
  @NotNull
  private Community community;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  @NotNull
  private Image image;
}
