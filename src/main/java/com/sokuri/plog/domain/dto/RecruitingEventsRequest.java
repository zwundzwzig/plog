package com.sokuri.plog.domain.dto;

import com.sokuri.plog.domain.Event;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecruitingEventsRequest {

    private String title;
    private String location;
    private String organizer;
    private LocalDateTime beginRecruit;
    private LocalDateTime finishRecruit;
    private LocalDateTime beginEvent;
    private LocalDateTime finishEvent;
    private List<String> thumbnails;

    public Event toEntity() {
        return Event.builder()
                .title(title)
                .location(location)
                .organizer(organizer)
                .beginEvent(beginEvent)
                .finishEvent(finishEvent)
                .build();
    }

}
