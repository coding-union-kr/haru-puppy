package com.developaw.harupuppy.domain.schedule.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
