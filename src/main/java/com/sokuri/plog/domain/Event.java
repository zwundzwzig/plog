package com.sokuri.plog.domain;

import com.sokuri.plog.domain.dto.event.EventDetailResponse;
import com.sokuri.plog.domain.dto.event.EventSummaryResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.relations.image.EventImage;
import com.sokuri.plog.domain.auditing.BaseTimeEntity;
import com.sokuri.plog.domain.converter.StringToUuidConverter;
import com.sokuri.plog.domain.embed.FestivalPeriod;
import com.sokuri.plog.domain.embed.RecruitPeriod;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.sokuri.plog.domain.converter.RoadNameAddressToCoordinateConverter.setAddressForSummary;

@Entity
@Table(name = "events")
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "event_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id;

    @Column(unique = true)
    @NotEmpty(message = "행사명은 필수 입력값이에요")
    private String title;

    @OneToMany(
            mappedBy = "event"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.PERSIST
            , orphanRemoval = true
    )
    private List<EventImage> images;

    @NotEmpty(message = "행사장 위치는 필수 입력값이에요")
    private String location;

    @Column
    @Setter
    private String description;

    @Column
    private String organizer;

    @Column
    private int dues;

    @Column
    @Setter
    private String website;

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

    public EventSummaryResponse toSummaryResponse() {
        return EventSummaryResponse.builder()
                .id(id)
                .title(title)
                .beginEvent(eventPeriod != null ? eventPeriod.getBeginEvent() : null)
                .finishEvent(eventPeriod != null ? eventPeriod.getFinishEvent() : null)
                .createdAt(getCreateDate())
                .images(!images.isEmpty()
                        ? images.stream()
                        .map(image -> image.getImage().getUrl())
                        .toList() : null)
                .organizer(organizer)
                .location(!location.isBlank()
                        ? setAddressForSummary(location)
                        : null)
                .dues(dues)
                .build();
    }

    public EventDetailResponse toDetailResponse() {
        return EventDetailResponse.builder()
                .title(title)
                .beginEvent(LocalDate.from(getEventPeriod().getBeginEvent()))
                .finishEvent(LocalDate.from(getEventPeriod().getFinishEvent()))
                .images(!images.isEmpty()
                        ? images.stream()
                        .map(image -> image.getImage().getUrl())
                        .toList() : null)
                .organizer(organizer)
                .location(setAddressForSummary(location))
                .dues(dues)
                .dueDate(Timestamp.valueOf(getRecruitPeriod().getFinishRecruit()))
                .website(website)
                .content(description)
                .numOfPeople(maxParticipants + "명")
                .build();
    }
}
