package com.sokuri.plog.domain.relations.image.composite;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.Image;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class EventImageId implements Serializable {
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private Event event;
}
