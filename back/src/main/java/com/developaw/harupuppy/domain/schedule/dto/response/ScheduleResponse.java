package com.developaw.harupuppy.domain.schedule.dto.response;

import com.developaw.harupuppy.domain.schedule.domain.AlertType;
import com.developaw.harupuppy.domain.schedule.domain.RepeatType;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.ScheduleType;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import java.time.LocalDateTime;
import java.util.List;

public record ScheduleResponse(
        Long scheduleId,
        ScheduleType scheduleType,
        LocalDateTime scheduleDateTime,
        String homeId,
        List<UserSchedule> mates,
        String repeatId,
        RepeatType repeatType,
        AlertType alertType,
        String memo,
        boolean isActive,
        boolean isDeleted
) {
    public static ScheduleResponse of(Schedule schedule) {
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getScheduleType(),
                schedule.getScheduleDateTime(),
                schedule.getHomeId(),
                schedule.getMates(),
                schedule.getRepeatId(),
                schedule.getRepeatType(),
                schedule.getAlertType(),
                schedule.getMemo(),
                schedule.isActive(),
                schedule.isDeleted()
        );
    }
}
