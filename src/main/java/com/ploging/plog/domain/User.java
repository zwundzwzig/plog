package com.ploging.plog.domain;

import com.ploging.plog.domain.eums.SocialProvider;
import com.ploging.plog.domain.utils.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_id")
  private UUID id; // 식별자 id

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String password;

  @Column
  private String birthday;

  @Enumerated(EnumType.STRING)
  @Column
  private SocialProvider provider;

}
