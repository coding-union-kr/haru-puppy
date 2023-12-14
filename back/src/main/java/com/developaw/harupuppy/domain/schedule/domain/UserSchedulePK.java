package com.developaw.harupuppy.domain.schedule.domain;

import com.developaw.harupuppy.domain.user.domain.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class UserSchedulePK implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public UserSchedulePK(User user, Schedule schedule) {
        this.user = user;
        this.schedule = schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
