package com.developaw.harupuppy.domain.schedule.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RepeatType {
    NONE("없음"),
    DAT("매일"),
    WEEK("매주"),
    MONTH("매월"),
    YEAR("매년");

    private final String desc;

    @JsonCreator
    public RepeatType fromString(String value) {
        return RepeatType.valueOf(value.toUpperCase());
    }
}
