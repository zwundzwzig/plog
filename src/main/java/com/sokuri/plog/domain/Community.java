package com.sokuri.plog.domain;

import com.sokuri.plog.domain.dto.RecruitingCommunitiesResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.relations.image.CommunityImage;
import com.sokuri.plog.domain.utils.BaseTimeEntity;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "communities")
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "community_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @NotBlank
    @Column(unique = true)
    private String title;

//    @Convert(converter = StringListConverter.class)
//    @Column(columnDefinition = "TEXT")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CommunityImage> images = new ArrayList<>();

    @NotBlank
    private String location;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User organizer;

    @Enumerated(EnumType.STRING)
    private RecruitStatus status;

    @Column(columnDefinition = "INT DEFAULT 100")
    private int maxParticipants;
    @Column(columnDefinition = "INT DEFAULT 0")
    private int currentParticipants;

    public RecruitingCommunitiesResponse toResponse() {
        return RecruitingCommunitiesResponse.builder()
                .title(title)
                .createdAt(LocalDate.from(getCreateDate()))
                .thumbnail(images.toString())
                .maxParticipants(maxParticipants)
                .location(location)
                .build();
    }

}
