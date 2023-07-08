package com.ploging.plog.domain.eums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitStatus {

    RECRUITING(0, "모집전"),
    FINISH(1, "모집중");

    private final int statusCode;
    private final String status;

}
