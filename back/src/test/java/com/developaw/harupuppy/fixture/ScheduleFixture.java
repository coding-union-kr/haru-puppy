package com.developaw.harupuppy.fixture;

import com.developaw.harupuppy.domain.schedule.domain.AlertType;
import com.developaw.harupuppy.domain.schedule.domain.RepeatType;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.ScheduleType;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.domain.UserRole;
import com.developaw.harupuppy.domain.user.dto.UserScheduleDto;
import java.util.List;

public class ScheduleFixture {
    public static ScheduleCreateRequest getCreateDto() {
        return new ScheduleCreateRequest(
                ScheduleType.POO,
                List.of(new UserScheduleDto(1L), new UserScheduleDto(2L)),
                "2023-12-25",
                "12:35",
                RepeatType.NONE,
                AlertType.NONE,
                ""
        );
    }
    public static ScheduleCreateRequest getWeeklyRepeatedDto() {
        return new ScheduleCreateRequest(
                ScheduleType.POO,
                List.of(new UserScheduleDto(1L), new UserScheduleDto(2L)),
                "2023-12-25",
                "12:35",
                RepeatType.WEEK,
                AlertType.NONE,
                ""
        );
    }
    public static ScheduleCreateRequest getMonthlyRepeatedDto() {
        return new ScheduleCreateRequest(
                ScheduleType.POO,
                List.of(new UserScheduleDto(1L), new UserScheduleDto(2L)),
                "2023-12-25",
                "12:35",
                RepeatType.MONTH,
                AlertType.NONE,
                ""
        );
    }

    public static ScheduleCreateRequest getDailyRepeatedDto() {
        return new ScheduleCreateRequest(
                ScheduleType.POO,
                List.of(new UserScheduleDto(1L), new UserScheduleDto(2L)),
                "2023-12-25",
                "12:35",
                RepeatType.DAY,
                AlertType.NONE,
                ""
        );
    }

    public static ScheduleCreateRequest getCreateDtoWithInvalidDateType() {
        return new ScheduleCreateRequest(
                ScheduleType.POO,
                List.of(new UserScheduleDto(1L), new UserScheduleDto(2L)),
                "0000-00-00",
                "00:00",
                RepeatType.NONE,
                AlertType.NONE,
                ""
        );
    }

    public static ScheduleCreateRequest getCreateDtoWithNullType() {
        return new ScheduleCreateRequest(
                ScheduleType.POO,
                null,
                "2023-12-25",
                "12:35",
                RepeatType.NONE,
                AlertType.NONE,
                ""
        );
    }

    public static List<UserSchedule> getUserSchedules(List<User> mates, Schedule schedule) {
        return UserSchedule.of(mates, schedule);
    }

    public static List<UserSchedule> getRepeatedUserSchedules(List<User> mates, List<Schedule> schedules) {
        return UserSchedule.of(mates, schedules);
    }

    public static List<User> getMates() {
        return List.of(User.builder()
                        .email("unni@test.com")
                        .nickname("언니")
                        .userImg("src")
                        .userRole(UserRole.UNNI)
                        .build(),
                User.builder()
                        .email("mom@test.com")
                        .nickname("엄마")
                        .userImg("src")
                        .userRole(UserRole.MOM)
                        .build()
        );
    }
}
