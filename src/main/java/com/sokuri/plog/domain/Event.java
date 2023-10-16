package com.sokuri.plog.domain;

import com.sokuri.plog.domain.dto.RecruitingEventsResponse;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.utils.BaseTimeEntity;
import com.sokuri.plog.domain.utils.StringListConverter;
import com.sokuri.plog.domain.utils.StringToUuidConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "event_id", columnDefinition = "BINARY(16) DEFAULT (UNHEX(REPLACE(UUID(), \"-\", \"\")))")
    @Convert(converter = StringToUuidConverter.class)
    private UUID id; // 식별자 id

    @NotBlank
    @Column(unique = true)
    private String title;

//    @Column
//    @Convert(converter = StringListConverter.class)
    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    @ToString.Exclude
    private final List<ImageEvent> images = new ArrayList<>();

    @NotBlank
    private String location;

    @Column
    private String description;

    @Column
    private String organizer;

    @Column
    private int dues;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('BEFORE', 'RECRUITING', 'FINISH') DEFAULT 'BEFORE'")
    @NotBlank
    private RecruitStatus status;

    @Embedded
    private RecruitPeriod recruitPeriod;

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime beginEvent;

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime finishEvent;

    public RecruitingEventsResponse toResponse() {
        return RecruitingEventsResponse.builder()
                .title(title)
                .beginEvent(LocalDate.from(beginEvent))
                .finishEvent(LocalDate.from(finishEvent))
                .thumbnail(images.toString())
                .organizer(organizer)
                .location(location)
                .dues(dues)
                .build();
    }

}
