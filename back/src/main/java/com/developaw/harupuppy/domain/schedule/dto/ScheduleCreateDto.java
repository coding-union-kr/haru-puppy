package com.developaw.harupuppy.domain.schedule.dto;

import com.developaw.harupuppy.domain.schedule.domain.AlertType;
import com.developaw.harupuppy.domain.schedule.domain.RepeatType;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.ScheduleType;
import com.developaw.harupuppy.domain.user.dto.ScheduleUserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public record ScheduleCreateDto(
        @NotNull(message = "스케줄 타입 지정이 필요합니다") ScheduleType scheduleType,
        @NotNull(message = "메이트 지정이 필요합니다") List<ScheduleUserDto> mates,
        @NotBlank(message = "스케줄 날짜 지정이 필요합니다") String scheduleDate,
        @NotBlank(message = "스케줄 시간 지정이 필요합니다") String scheduleTime,
        RepeatType repeatType,
        AlertType alertType,
        String memo
) {
    public ScheduleCreateDto {
        Objects.requireNonNull(scheduleType, "스케줄 타입은 필수값 입니다");
        Objects.requireNonNull(mates, "메이트는 필수값 입니다");
        Objects.requireNonNull(scheduleDate, "스케줄 날짜는 필수값 입니다");
        Objects.requireNonNull(scheduleTime, "스케줄 시간은 필수값 입니다");
    }

    public static Schedule fromDto(ScheduleCreateDto dto) {
        validateDateTime(dto.scheduleDate, dto.scheduleTime);
        return Schedule.builder()
                .scheduleDateTime(Schedule.parseDateTime(dto.scheduleDate(), dto.scheduleTime()))
                .scheduleType(dto.scheduleType())
                .alertType(dto.alertType())
                .repeatType(dto.repeatType())
                .memo(dto.memo())
                .build();
    }
    private static void validateDateTime(String date, String time) {
        if (date == null || time == null) {
            throw new IllegalArgumentException("Date and time must not be null");
        }
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date or time format", e);
        }
    }
}
