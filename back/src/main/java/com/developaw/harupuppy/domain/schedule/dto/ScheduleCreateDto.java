package com.developaw.harupuppy.domain.schedule.dto;

import com.developaw.harupuppy.domain.schedule.domain.AlertType;
import com.developaw.harupuppy.domain.schedule.domain.RepeatType;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.ScheduleType;
import com.developaw.harupuppy.domain.user.dto.UserScheduleDto;
import com.developaw.harupuppy.global.utils.DateUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public record ScheduleCreateDto(
        @NotNull(message = "스케줄 타입 지정이 필요합니다") ScheduleType scheduleType,
        @NotNull(message = "메이트 지정이 필요합니다") List<UserScheduleDto> mates,
        @NotBlank(message = "스케줄 날짜 지정이 필요합니다") String scheduleDate,
        @NotBlank(message = "스케줄 시간 지정이 필요합니다") String scheduleTime,
        RepeatType repeatType,
        AlertType alertType,
        String memo
) {

    public static Schedule fromDto(ScheduleCreateDto dto) {
        DateUtils.validateDateTime(dto.scheduleDate, dto.scheduleTime);
        return Schedule.builder()
                .scheduleDateTime(DateUtils.parseDateTime(dto.scheduleDate(), dto.scheduleTime()))
                .scheduleType(dto.scheduleType())
                .mates(new ArrayList<>())
                .alertType(dto.alertType())
                .repeatType(dto.repeatType())
                .memo(dto.memo())
                .build();
    }
}
