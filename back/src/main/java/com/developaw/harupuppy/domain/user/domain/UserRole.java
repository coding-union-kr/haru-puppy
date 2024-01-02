package com.developaw.harupuppy.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    DAD("아빠"),
    MOM("엄마"),
    UNNI("언니"),
    OPPA("오빠"),
    NUNA("누나"),
    HYEONG("형"),
    YOUNGER("동생");

    private final String desc;

    @JsonCreator
    public UserRole fromString(String value){
        return UserRole.valueOf(value.toUpperCase());
    }
}
