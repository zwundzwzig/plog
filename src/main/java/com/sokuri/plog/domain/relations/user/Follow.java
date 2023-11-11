package com.sokuri.plog.domain.relations.user;

import com.sokuri.plog.domain.User;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
  @Id
  @GeneratedValue(generator = "follow")
  @GenericGenerator(name = "follow", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "follow_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
  @Convert(converter = StringToUuidConverter.class)
  private UUID id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_user_id")
  private User toUser;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_user_id")
  private User fromUser;
}
