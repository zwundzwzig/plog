package com.ploging.plog.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
