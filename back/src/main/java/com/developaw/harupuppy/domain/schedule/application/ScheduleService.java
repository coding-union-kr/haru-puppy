package com.developaw.harupuppy.domain.schedule.application;

import com.developaw.harupuppy.domain.schedule.dao.ScheduleRepository;
import com.developaw.harupuppy.domain.schedule.dao.UserScheduleRepository;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateDto;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.dto.UserScheduleDto;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserScheduleRepository userScheduleRepository;
    private final UserRepository userRepository;

    public void create(ScheduleCreateDto dto) {
        List<User> mates = validateMates(dto.mates());

        Schedule schedule = ScheduleCreateDto.fromDto(dto);
        scheduleRepository.save(schedule);

        List<UserSchedule> userSchedules = UserSchedule.of(mates, schedule);
        userSchedules.forEach(schedule::addMate);

        userScheduleRepository.saveAll(userSchedules);
    }

    private List<User> validateMates(List<UserScheduleDto> mates) {
        return mates.stream().map(mate -> {
            User user = userRepository.findById(mate.userID()).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_USER));
            return user;
        }).collect(Collectors.toList());
    }
}
