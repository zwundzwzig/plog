package com.sokuri.plog.domain;

import com.sokuri.plog.domain.dto.community.CommunityDetailResponse;
import com.sokuri.plog.domain.dto.community.CommunitySummaryResponse;
import com.sokuri.plog.domain.dto.user.UserSimpleDto;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.relations.image.CommunityImage;
import com.sokuri.plog.domain.auditing.BaseTimeEntity;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import com.sokuri.plog.domain.embed.FestivalPeriod;
import com.sokuri.plog.domain.embed.RecruitPeriod;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.*;

import static com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter.setAddressForSummary;

@Entity
@Table(name = "communities")
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Community extends BaseTimeEntity {
    @Id
    @GeneratedValue(generator = "community")
    @GenericGenerator(name = "community", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "community_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @Column(unique = true)
    @NotEmpty(message = "크루명은 필수 입력값이에요")
    private String title;

    @OneToMany(
            mappedBy = "community"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.PERSIST
            , orphanRemoval = true
    )
    private List<CommunityImage> images;

    @NotEmpty(message = "모임 위치는 필수 입력값이에요")
    private String location;

    @Column
    @Setter
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer")
    @NotNull(message = "유저는 필수 입력값이에요")
    @Setter
    private User organizer;

    @Column(columnDefinition = "INT DEFAULT 100")
    private int maxParticipants;

    @Column(columnDefinition = "INT DEFAULT 0")
    @Setter
    private int currentParticipants;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('BEFORE', 'RECRUITING', 'FINISH') DEFAULT 'BEFORE'")
    private RecruitStatus status;

    @Embedded
    private RecruitPeriod recruitPeriod;

    @Embedded
    private FestivalPeriod eventPeriod;

    @Column
    @Setter
    private String link;

    public CommunitySummaryResponse toSummaryResponse() {
        return CommunitySummaryResponse.builder()
                .id(id)
                .title(title)
                .images(!images.isEmpty()
                        ? images.stream()
                        .map(image -> image.getImage().getUrl())
                        .toList() : null)
                .maxParticipants(maxParticipants)
                .createdAt(getCreateDate())
                .currentParticipants(currentParticipants)
                .location(location)
                .build();
    }

    public CommunityDetailResponse toDetailResponse() {
        return CommunityDetailResponse.builder()
                .title(title)
                .images(!images.isEmpty()
                        ? images.stream()
                        .map(image -> image.getImage().getUrl())
                        .toList() : null)
                .organizer(new UserSimpleDto(organizer.getId(), organizer.getNickname(), organizer.getProfileImage()))
                .location(setAddressForSummary(location))
                .content(description)
                .link(link)
                .beginEvent(eventPeriod != null ? eventPeriod.getBeginEvent() : null)
                .finishEvent(eventPeriod != null ? eventPeriod.getFinishEvent() : null)
                .dueDate(recruitPeriod != null ? recruitPeriod.getFinishRecruit() : null)
                .numOfPeople(currentParticipants + "/" + maxParticipants + "명")
                .build();
    }
}
