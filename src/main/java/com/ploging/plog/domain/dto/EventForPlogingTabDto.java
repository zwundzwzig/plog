package com.ploging.plog.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventForPlogingTabDto {

    private String title;
    private String location;
    private String organizer;
    private LocalDateTime expiredRecruit;
    private LocalDateTime beginEvent; // 행사
    private LocalDateTime expiredEvent;

}
