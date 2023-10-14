package com.sokuri.plog.domain.eums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitStatus implements BaseEnumCode<String> {

    BEFORE("모집전"),
    RECRUITING("모집중"),
    FINISH("모집완료");

    private final String status;
    public static final RecruitStatus DEFAULT = BEFORE;

    @Override
    public String getValue() {
        return this.status;
    }
}
