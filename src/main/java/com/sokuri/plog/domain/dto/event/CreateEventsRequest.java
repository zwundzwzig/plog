package com.sokuri.plog.domain.dto.event;

import com.sokuri.plog.domain.Event;
import com.sokuri.plog.domain.eums.RecruitStatus;
import com.sokuri.plog.domain.utils.FestivalPeriod;
import com.sokuri.plog.domain.utils.RecruitPeriod;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateEventsRequest {
    private String title;
    private String location;
    private String organizer;
    private LocalDateTime beginRecruit;
    private LocalDateTime finishRecruit;
    private LocalDateTime beginEvent;
    private LocalDateTime finishEvent;
    private Integer dues;
    private String website;
    private String description;
    private Integer maxParticipants;
    private List<MultipartFile> images;

    public Event toEntity() {
        return Event.builder()
                .title(title)
                .website(website)
                .recruitPeriod(new RecruitPeriod(beginRecruit, finishRecruit))
                .location(location)
                .status(RecruitStatus.DEFAULT)
                .maxParticipants(maxParticipants)
                .dues(dues)
                .organizer(organizer)
                .eventPeriod(new FestivalPeriod(beginEvent, finishRecruit))
                .description(description)
                .build();
    }
}
