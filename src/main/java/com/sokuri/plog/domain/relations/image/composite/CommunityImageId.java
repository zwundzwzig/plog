package com.sokuri.plog.domain.relations.image.composite;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.Image;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class CommunityImageId implements Serializable {
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "community_id")
  private Community community;
}
