package com.sokuri.plog.domain.relations.user;

import com.sokuri.plog.domain.entity.Community;
import com.sokuri.plog.domain.entity.User;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_communities")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCommunity {
    @Id
    @GeneratedValue(generator = "user_community")
    @GenericGenerator(name = "user_community", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;
}