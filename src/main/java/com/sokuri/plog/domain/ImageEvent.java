package com.sokuri.plog.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "image_events")
@Getter
public class ImageEvent {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "image_id")
  private Image image;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private Event event;
}
