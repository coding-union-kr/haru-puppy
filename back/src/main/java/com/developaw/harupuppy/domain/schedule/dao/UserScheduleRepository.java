package com.developaw.harupuppy.domain.schedule.dao;

import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {
    Optional<List<UserSchedule>> findAllByScheduleId(Long scheduleId);
    void deleteByScheduleIdIn(List<Long> scheduleIds);
}
