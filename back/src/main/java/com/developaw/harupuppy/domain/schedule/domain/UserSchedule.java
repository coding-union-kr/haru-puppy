package com.developaw.harupuppy.domain.schedule.domain;

import com.developaw.harupuppy.domain.user.domain.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER_SCHEDULES")
public class UserSchedule {

    @EmbeddedId
    private UserSchedulePK userSchedulePK;

    public UserSchedule(UserSchedulePK userSchedulePK) {
        this.userSchedulePK = userSchedulePK;
    }

    public static List<UserSchedule> of(List<User> mates, Schedule schedule) {
        return mates.stream().map(user -> {
            return new UserSchedule(new UserSchedulePK(user, schedule));
        }).collect(Collectors.toList());
    }
    public static List<UserSchedule> of(List<User> mates, List<Schedule> repeatSchedules) {
        return repeatSchedules.stream()
                .flatMap(schedule -> mates.stream().map(user -> {
                    return new UserSchedule(new UserSchedulePK(user, schedule));
                }))
                .collect(Collectors.toList());
    }
}
