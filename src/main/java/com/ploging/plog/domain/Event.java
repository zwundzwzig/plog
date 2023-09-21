package com.ploging.plog.domain;

import com.ploging.plog.domain.eums.RecruitStatus;
import com.ploging.plog.domain.utils.BaseTimeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events")
@Getter
@RequiredArgsConstructor
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "event_id", columnDefinition = "CHAR(36)")
    private UUID id; // 식별자 id

    @NotBlank
    private String title;

    @Column
    private String image;

    @NotBlank
    private String location;

    @Column
    private String description;

    @Column
    private String organizer;

    @Column
    private int dues;

    @Enumerated(EnumType.STRING)
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
