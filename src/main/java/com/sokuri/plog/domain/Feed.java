package com.sokuri.plog.domain;

import com.sokuri.plog.domain.converter.DateToStringConverter;
import com.sokuri.plog.domain.dto.feed.FeedDetailResponse;
import com.sokuri.plog.domain.dto.feed.FeedSummaryResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.domain.relations.hashtag.FeedHashtag;
import com.sokuri.plog.domain.relations.image.FeedImage;
import com.sokuri.plog.domain.utils.BaseTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "feeds")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Feed extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "feed_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Setter
    private User user;

    @OneToMany(
            mappedBy = "feed",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private List<FeedImage> images;

    @Column
    @Setter
    private String description;

    @OneToMany(
            mappedBy = "feed",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private Set<FeedHashtag> hashtags;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PRIVATE', 'PARTIAL', 'PUBLIC') DEFAULT 'PUBLIC'")
    private AccessStatus status;

    public FeedSummaryResponse toSummaryResponse() {
        return FeedSummaryResponse.builder()
                .id(id)
                .nickname(user.getNickname())
                .avatar(user.getProfileImage())
                .createdAt(LocalDate.from(getCreateDate()))
                .images(!images.isEmpty()
                        ? images.stream()
                        .map(image -> image.getImage().getUrl())
                        .toList() : null)
                .timeSinceUpload(DateToStringConverter.explainDate(getCreateDate()))
                .build();
    }

    public FeedDetailResponse toDetailResponse() {
        return FeedDetailResponse.builder()
                .nickname(user.getNickname())
                .avatar(user.getProfileImage())
                .createdAt(getCreateDate())
                .description(description)
                .images(!images.isEmpty()
                        ? images.stream()
                        .map(image -> image.getImage().getUrl())
                        .toList() : null)
                .timeSinceUpload(DateToStringConverter.explainDate(getCreateDate()))
                .hashtags(!hashtags.isEmpty()
                        ? hashtags.stream()
                        .map(hashtag -> hashtag.getHashtag().getName())
                        .toList() : null)
                .build();
    }
}
