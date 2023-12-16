package com.developaw.harupuppy.domain.schedule.dto;

import jakarta.validation.constraints.NotNull;

public record ScheduleDeleteRequest(
    @NotNull(message = "스케줄 아이디가 필요합니다") Long scheduleId,
    String repeatId,
    @NotNull boolean deleteAllSchedules
){
}
