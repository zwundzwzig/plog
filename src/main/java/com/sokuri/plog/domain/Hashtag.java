package com.sokuri.plog.domain;

import com.sokuri.plog.domain.relations.hashtag.FeedHashtag;
import com.sokuri.plog.domain.auditing.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "hashtags")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "hashtag_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    private UUID id;

    @Column
    private String name;

    @OneToMany(
            mappedBy = "hashtag"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.PERSIST
            , orphanRemoval = true
    )
    @Builder.Default
    private Set<FeedHashtag> feeds = new HashSet<>();
}
