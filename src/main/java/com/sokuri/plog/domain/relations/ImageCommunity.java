package com.sokuri.plog.domain.relations;

import com.sokuri.plog.domain.Community;
import com.sokuri.plog.domain.Image;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "image_communities")
@Getter
public class ImageCommunity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "community_id")
  private Community community;
}
