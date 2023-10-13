package com.sokuri.plog.domain;

import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.utils.BaseTimeEntity;
import com.sokuri.plog.domain.utils.StringListConverter;
import com.sokuri.plog.domain.utils.StringToUuidConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@RequiredArgsConstructor
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
    private String title;

//    @Column
    @Convert(converter = StringListConverter.class)
    private List<String> images = new ArrayList<>();

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
    private RecruitStatus status;

    @Embedded
    private RecruitPeriod recruitPeriod;

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime beginEvent; // 행사

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime finishEvent;

}
