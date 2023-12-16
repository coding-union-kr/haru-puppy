package com.developaw.harupuppy.domain.schedule.dto;

import com.developaw.harupuppy.domain.schedule.domain.AlertType;
import com.developaw.harupuppy.domain.schedule.domain.RepeatType;
import com.developaw.harupuppy.domain.schedule.domain.ScheduleType;
import com.developaw.harupuppy.domain.user.dto.UserScheduleDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ScheduleUpdateRequest(
        @NotNull(message = "스케줄 아이디가 필요합니다") Long scheduleId,
        @NotNull(message = "스케줄 타입 지정이 필요합니다") ScheduleType scheduleType,
        @NotNull(message = "메이트 지정이 필요합니다") List<UserScheduleDto> mates,
        @NotBlank(message = "스케줄 날짜 지정이 필요합니다") String scheduleDate,
        @NotBlank(message = "스케줄 시간 지정이 필요합니다") String scheduleTime,
        String repeatId,
        RepeatType repeatType,
        AlertType alertType,
        String memo,
        Boolean modifyRepeatedSchedules
) {
}
