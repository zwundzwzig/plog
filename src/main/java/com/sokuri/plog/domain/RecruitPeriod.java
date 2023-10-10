package com.sokuri.plog.domain;

import lombok.Getter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class RecruitPeriod {

    @Column()
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime beginRecruit; // 모집

    @Column
    @Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
    private LocalDateTime finishRecruit;

}
