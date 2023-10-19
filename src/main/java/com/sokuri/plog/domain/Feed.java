package com.sokuri.plog.domain;

import com.sokuri.plog.domain.converter.DateToStringConverter;
import com.sokuri.plog.domain.dto.FeedsResponse;
import com.sokuri.plog.domain.eums.AccessStatus;
import com.sokuri.plog.domain.relations.hashtag.FeedHashtag;
import com.sokuri.plog.domain.relations.image.FeedImage;
import com.sokuri.plog.domain.utils.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "feeds")
@Getter
@NoArgsConstructor
public class Feed extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "feed_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FeedImage> images;

    @Column
    private String description;

    @OneToMany(mappedBy = "feed", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FeedHashtag> hashtags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PRIVATE', 'PARTIAL', 'PUBLIC') DEFAULT 'PUBLIC'")
    @NotBlank
    private AccessStatus status;

    public FeedsResponse toResponse() {
        return FeedsResponse.builder()
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
}
