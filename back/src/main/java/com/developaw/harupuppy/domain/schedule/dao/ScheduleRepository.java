package com.developaw.harupuppy.domain.schedule.dao;

import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
