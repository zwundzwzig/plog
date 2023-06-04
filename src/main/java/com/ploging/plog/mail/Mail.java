package com.ploging.plog.mail;

import com.ploging.plog.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mails")
@Getter
@NoArgsConstructor
public class Mail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mail_id")
  private UUID id; // 식별자 id

  @Column(length = 500, nullable = false)
  private String title; // 메일 제목

  @Column(columnDefinition = "TEXT", nullable = false)
  private String content; // 메일 내용

  @Column(nullable = false)
  private LocalDateTime sendTime; // 보낸 시간

  @ManyToOne
  @Column(nullable = false)
  private User sender;

  @ManyToOne
  @Column(nullable = false)
  private User recipient;

  @OneToMany(mappedBy = "mail")
  private List<Attachment> attachments;

}