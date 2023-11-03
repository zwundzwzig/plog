package com.sokuri.plog.domain;

import com.sokuri.plog.domain.auditing.BaseTimeEntity;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name="uuid2", strategy = "uuid2")
  @Column(name = "user_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;

  @Column(nullable = false, unique = true)
  @NotEmpty(message = "닉네임은 필수 입력값이에요")
  private String nickname;

  @Column(nullable = false, unique = true)
  @Email(message = "메일 형식에 맞춰 작성해주세요",
          regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
  @NotEmpty(message = "메일 형식에 맞춰 작성해주세요")
  private String email;

  @Column
  private String password;

  @Column
  private LocalDate birthday;

  @Column
  private String profileImage;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Feed> feeds;
}
