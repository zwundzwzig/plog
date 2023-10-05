package com.ploging.plog.domain;

import com.ploging.plog.domain.utils.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
  @GenericGenerator(name="uuid2", strategy = "uuid2")
  @Column(name = "user_id", columnDefinition = "BINARY(16) DEFAULT UUID()")
  private UUID id; // 식별자 id

  @Column(nullable = false, unique = true)
  private String nickname;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column
  private String birthday;

  @OneToOne
  @JoinColumn(name = "profile_image_id")
  private Image profileImage;

  @OneToMany(mappedBy = "user")
  private List<Feed> feeds;

}
