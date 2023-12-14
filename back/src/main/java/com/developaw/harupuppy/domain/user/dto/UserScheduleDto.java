package com.developaw.harupuppy.domain.user.dto;

import java.util.Objects;

public record UserScheduleDto(Long userID) {
    public UserScheduleDto {
        Objects.requireNonNull(userID);
    }
}
