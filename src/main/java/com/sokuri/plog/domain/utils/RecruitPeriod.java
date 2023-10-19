package com.sokuri.plog.domain.utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@ToString
public class RecruitPeriod {

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime beginRecruit;

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime finishRecruit;

}
