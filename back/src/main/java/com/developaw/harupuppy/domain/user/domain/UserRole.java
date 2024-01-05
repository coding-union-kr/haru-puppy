package com.developaw.harupuppy.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    DAD("아빠"),
    MOM("엄마"),
    UNNIE("언니"),
    OPPA("오빠"),
    NUNA("누나"),
    HYEONG("형"),
    YOUNGER("동생");

    private final String desc;
}
