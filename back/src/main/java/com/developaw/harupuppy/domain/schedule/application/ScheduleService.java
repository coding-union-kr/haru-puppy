package com.developaw.harupuppy.domain.schedule.application;

import com.developaw.harupuppy.domain.schedule.dao.ScheduleRepository;
import com.developaw.harupuppy.domain.schedule.dao.UserScheduleRepository;
import com.developaw.harupuppy.domain.schedule.domain.RepeatType;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.developaw.harupuppy.domain.schedule.dto.response.ScheduleResponse;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.dto.UserScheduleDto;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import java.time.LocalDateTime;
import java.time.YearMonth;
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
    public ScheduleResponse create(ScheduleCreateRequest dto) {
        String homeId = "";// 매개변수로 넘어온 userDto에서 homeId 꺼내기
        List<User> mates = validateMates(dto.mates());

        Schedule schedule = ScheduleCreateRequest.fromDto(dto, homeId);
        scheduleRepository.save(schedule);

        List<UserSchedule> userSchedules = UserSchedule.of(mates, schedule);
        userSchedules.forEach(schedule::addMate);
        userScheduleRepository.saveAll(userSchedules);

        if (dto.repeatType() != null && !RepeatType.NONE.equals(dto.repeatType())) {
            createRepeatSchedule(schedule, mates);
        }

        return ScheduleResponse.of(schedule);
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
    public ScheduleResponse update(Long scheduleId, ScheduleUpdateRequest dto, boolean all) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
        List<UserSchedule> newMates = UserSchedule.of(validateMates(dto.mates()), schedule);
        schedule.update(dto, newMates);
        if (dto.repeatId() != null && all) {
            String repeatId = Objects.requireNonNull(dto.repeatId());
            List<Schedule> repeatedSchedules = scheduleRepository.findAllByRepeatIdAndScheduleDateTimeAfter(repeatId,
                            schedule.getScheduleDateTime())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
            repeatedSchedules.forEach(repeatSchedule -> repeatSchedule.update(dto, newMates));
        }
        return ScheduleResponse.of(schedule);
    }

    @Transactional
    public void delete(Long scheduleId, boolean all) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        if (schedule.getRepeatId() != null && all) {
            List<Schedule> repeatedSchedules = scheduleRepository.findAllByRepeatIdAndScheduleDateTimeAfter(
                            schedule.getRepeatId(), schedule.getScheduleDateTime())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
            scheduleRepository.deleteAll(repeatedSchedules);
        } else {
            scheduleRepository.delete(schedule);
        }
    }

    @Transactional
    public ScheduleResponse updateStatus(Long scheduleId, boolean status) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
        if (status) {
            schedule.done();
        } else {
            schedule.planned();
        }
        return ScheduleResponse.of(schedule);
    }

    public ScheduleResponse get(Long ScheduleId) {
        Schedule schedule = scheduleRepository.findById(ScheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
        return ScheduleResponse.of(schedule);
    }

    public List<ScheduleResponse> getSchedules(String homeId, int year, int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59);
        List<Schedule> scheduleList = scheduleRepository.findAllByHomeIdAndScheduleDateTimeBetweenOrderByScheduleDateTimeAsc(
                        homeId, startDate, endDate)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        return scheduleList.stream()
                .map(schedule -> ScheduleResponse.of(schedule))
                .collect(Collectors.toList());
    }

    public static List<LocalDateTime> getDateTimesUntilNextYear(RepeatType type,
                                                                LocalDateTime startDate,
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
