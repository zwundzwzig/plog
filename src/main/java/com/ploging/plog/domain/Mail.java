package com.ploging.plog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
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
  private UUID id; // 식별자 id

  @Column(length = 500, nullable = false)
  private String title; // 메일 제목

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content; // 메일 내용

  @Column(nullable = false)
  private LocalDateTime sendTime; // 보낸 시간

//  @ManyToOne
//  @Column(nullable = false)
//  private User sender;

//  @ManyToOne
//  @Column(nullable = false)
//  private User receiver;

//  @OneToMany(mappedBy = "mail")
//  private List<Attachment> attachments;
  @Column
  @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
  private LocalDateTime created; // 생성

  @Column
  @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
  private LocalDateTime modified;


}