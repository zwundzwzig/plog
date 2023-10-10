package com.sokuri.plog.domain.eums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitStatus {

    BEFORE("모집전"),
    RECRUITING("모집중"),
    FINISH("모집완료");

    private final String status;

}
