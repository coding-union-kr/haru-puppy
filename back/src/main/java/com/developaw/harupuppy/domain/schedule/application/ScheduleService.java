package com.developaw.harupuppy.domain.schedule.application;

import com.developaw.harupuppy.domain.schedule.dao.ScheduleRepository;
import com.developaw.harupuppy.domain.schedule.dao.UserScheduleRepository;
import com.developaw.harupuppy.domain.schedule.domain.RepeatType;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateDto;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleModifyDto;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.dto.UserScheduleDto;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserScheduleRepository userScheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(ScheduleCreateDto dto) {
        List<User> mates = validateMates(dto.mates());

        Schedule schedule = ScheduleCreateDto.fromDto(dto);
        scheduleRepository.save(schedule);

        List<UserSchedule> userSchedules = UserSchedule.of(mates, schedule);
        userSchedules.forEach(schedule::addMate);
        userScheduleRepository.saveAll(userSchedules);

        if (dto.repeatType() != null && !RepeatType.NONE.equals(dto.repeatType())) {
            createRepeatSchedule(schedule, mates);
        }
    }

    @Transactional
    public void createRepeatSchedule(Schedule schedule, List<User> mates) {
        LocalDateTime startDate = schedule.getScheduleDateTime();
        LocalDateTime endDate = startDate.plusYears(1).withMonth(12).withDayOfMonth(31);

        String repeatId = UUID.randomUUID().toString();
        schedule.setRepeatId(repeatId);// 등록 일자의 스케줄에도 repeat id 부여
        log.info("{}, {}", schedule.getRepeatId(), schedule.getRepeatType());

        List<LocalDateTime> dateTimesUntilNextYear = getDateTimesUntilNextYear(schedule.getRepeatType(), startDate,
                endDate, new ArrayList<>());
        log.info("datetime size : {}", dateTimesUntilNextYear.size());

        List<Schedule> schedules = Schedule.of(dateTimesUntilNextYear, schedule, repeatId);
        scheduleRepository.saveAll(schedules);
        log.info("schedule size : {}", schedules.size());

        List<UserSchedule> userSchedules = UserSchedule.of(mates, schedules);
        userSchedules.forEach(schedule::addMate);
        userScheduleRepository.saveAll(userSchedules);
        log.info("userSchedules size : {}", userSchedules.size());
    }

    @Transactional
    public void update(ScheduleModifyDto dto){
        Schedule schedule = scheduleRepository.findById(dto.scheduleId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
        List<UserSchedule> newMates = UserSchedule.of(validateMates(dto.mates()), schedule);

        if(dto.modifyRepeatedSchedules()){
            String repeatId = Objects.requireNonNull(dto.repeatId());
            List<Schedule> repeatedSchedules = scheduleRepository.findAllByRepeatId(repeatId)
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
            repeatedSchedules.forEach(repeatSchedule -> repeatSchedule.update(dto, newMates));
        }else{
            schedule.update(dto, newMates);
        }
    }

    public static List<LocalDateTime> getDateTimesUntilNextYear(RepeatType type, LocalDateTime startDate,
                                                                LocalDateTime endDate,
                                                                List<LocalDateTime> repeatedDateTimes) {
        LocalDateTime current = null;
        switch (type) {
            case DAY -> {
                current = startDate.plusDays(1);
                while (current.isBefore(endDate)) {
                    repeatedDateTimes.add(current);
                    current = current.plusDays(1);
                }
            }
            case WEEK -> {
                current = startDate.plusWeeks(1);
                while (current.isBefore(endDate)) {
                    repeatedDateTimes.add(current);
                    current = current.plusWeeks(1);
                }
            }
            case MONTH -> {
                current = startDate.plusMonths(1);
                while (current.isBefore(endDate)) {
                    repeatedDateTimes.add(current);
                    current = current.plusMonths(1);
                }
            }
            case YEAR -> {
                repeatedDateTimes = List.of(startDate.plusYears(1));
            }
        }
        return repeatedDateTimes;
    }

    private List<User> validateMates(List<UserScheduleDto> mates) {
        return mates.stream().map(mate -> {
            User user = userRepository.findById(mate.userID()).orElseThrow(
                    () -> new CustomException(ErrorCode.NOT_FOUND_USER));
            return user;
        }).collect(Collectors.toList());
    }
}
