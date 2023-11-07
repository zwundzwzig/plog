package com.sokuri.plog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "mails")
@Getter
@NoArgsConstructor
public class Mail {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "mail_id")
  private UUID id;

  @Column(length = 500, nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content; // 메일 내용

  @Column(nullable = false)
  private LocalDateTime sendTime; // 보낸 시간

  @Column
  @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
  private LocalDateTime created; // 생성

  @Column
  @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
  private LocalDateTime modified;
}