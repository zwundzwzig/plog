package com.ploging.plog.mail;

import com.ploging.plog.mail.Mail;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "attachments")
@Getter
@NoArgsConstructor
public class Attachment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attachment_id")
  private UUID id; // 식별자 id

  @Column(nullable = false)
  private String fileName;

  @Column(nullable = false)
  private long fileSize;

  @Column(nullable = false)
  private String fileType;

  @ManyToOne
  @Column(nullable = false)
  private Mail mail;

}
