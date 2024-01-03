package com.sokuri.plog.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Getter
@NoArgsConstructor
public class Attachment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "attachment_id")
  private UUID id;

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private long fileSize;

  @Column(nullable = false)
  private String fileType;
}
