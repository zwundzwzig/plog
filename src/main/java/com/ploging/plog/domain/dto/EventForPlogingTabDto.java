package com.ploging.plog.domain.dto;

import com.ploging.plog.domain.Event;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventForPlogingTabDto {

    private String title;
    private String location;
    private String organizer;
    private LocalDateTime beginRecruit;
    private LocalDateTime finishRecruit;
    private LocalDateTime beginEvent;
    private LocalDateTime finishEvent;

    public EventForPlogingTabDto(Event entity) {
        this.title = entity.getTitle();
        this.location = entity.getLocation();
        this.organizer = entity.getOrganizer();
        this.beginRecruit = entity.getRecruitPeriod().getBeginRecruit();
        this.finishRecruit = entity.getRecruitPeriod().getFinishRecruit();
        this.beginEvent = entity.getBeginEvent();
        this.finishEvent = entity.getFinishEvent();
    }

}
