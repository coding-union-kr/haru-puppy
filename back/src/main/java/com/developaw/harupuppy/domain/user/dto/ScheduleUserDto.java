package com.developaw.harupuppy.domain.user.dto;

import java.util.Objects;
public record ScheduleUserDto(Long userID) {
    public ScheduleUserDto {
        Objects.requireNonNull(userID);
    }
}
