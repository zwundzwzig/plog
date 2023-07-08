package com.ploging.plog.domain;

import com.ploging.plog.domain.eums.RecruitStatus;
import com.ploging.plog.domain.utils.BaseTimeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
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
    private String dues;

    @Column
    private RecruitStatus status;

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime beginRecruit; // 모집

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime finishRecruit;

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime beginEvent; // 행사

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime finishEvent;

}
