package com.developaw.harupuppy.domain.schedule.dao;

import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedulePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, UserSchedulePK> {
}
