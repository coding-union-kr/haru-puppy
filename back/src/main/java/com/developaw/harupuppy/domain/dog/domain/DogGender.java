package com.developaw.harupuppy.domain.dog.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DogGender {
    MALE("남아"),
    FEMALE("여아");

    private final String desc;

    @JsonCreator
    public DogGender fromString(String value) {
        return DogGender.valueOf(value.toUpperCase());
    }
}
