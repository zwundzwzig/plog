package com.sokuri.plog.domain.relations.image;

import com.sokuri.plog.domain.relations.image.composite.CommunityImageId;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "community_images")
@Getter
public class CommunityImage {
  @EmbeddedId
  private CommunityImageId id;
}
