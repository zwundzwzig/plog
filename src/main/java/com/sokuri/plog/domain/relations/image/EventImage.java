package com.sokuri.plog.domain.relations.image;

import com.sokuri.plog.domain.relations.image.composite.EventImageId;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "event_images")
@Getter
public class EventImage {
  @EmbeddedId
  private EventImageId Id;
}
