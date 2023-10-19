package com.sokuri.plog.domain.eums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrashType implements BaseEnumCode<String> {

    TRASH("일반"),
    GARBAGE("음식물"),
    RECYCLABLE("재활용"),
    CIGARETTE("담배꽁초");

    private final String value;
    public static final TrashType DEFAULT = TRASH;

    @Override
    public String getValue() {
        return this.value;
    }

}
