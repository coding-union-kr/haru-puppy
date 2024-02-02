package com.developaw.harupuppy.domain.schedule.dao;

import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<List<Schedule>> findAllByRepeatIdAndScheduleDateTimeAfter(String repeatId, LocalDateTime scheduleDateTime);
    Optional<List<Schedule>> findAllByRepeatIdAndScheduleDateTimeGreaterThanEqual(String repeatId, LocalDateTime scheduleDateTime);
    Optional<List<Schedule>> findAllByHomeIdAndScheduleDateTimeBetweenOrderByScheduleDateTimeAsc(String homeId, LocalDateTime startDate, LocalDateTime endDate);
}
