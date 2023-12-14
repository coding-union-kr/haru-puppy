package com.developaw.harupuppy.domain.schedule.application;

import com.developaw.harupuppy.domain.schedule.dao.ScheduleRepository;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateDto;
import com.developaw.harupuppy.domain.user.dto.ScheduleUserDto;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public void create(ScheduleCreateDto dto) {
        //TODO :: 요청한 유저정보가 서비스에 가입된 유저인지 확인
        validateMates(dto.mates());
        Schedule schedule = ScheduleCreateDto.fromDto(dto);
        scheduleRepository.save(schedule);
    }

    private void validateMates(List<ScheduleUserDto> mates) {
        mates.forEach(mate -> {
            userRepository.findById(mate.userID()).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_USER)
            );
        });
    }
}
