package com.sokuri.plog.domain.dto;

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
}
