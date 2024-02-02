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
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.dto.UserScheduleDto;
import com.developaw.harupuppy.domain.user.repository.HomeRepository;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
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
    private final HomeRepository homeRepository;

    @Transactional
    public ScheduleResponse create(ScheduleCreateRequest dto, UserDetail userDto) {
        String homeId = userDto.getHomeId();
        homeRepository.findByHomeId(homeId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOME));

        List<User> mates = validateMates(dto.mates());
        Schedule schedule = ScheduleCreateRequest.fromDto(dto, homeId, userDto.getUserId());
        scheduleRepository.save(schedule);

        List<UserSchedule> userSchedules = UserSchedule.of(mates, schedule);
        schedule.getMates().addAll(userSchedules);

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

        List<LocalDateTime> dateTimesUntilNextYear = getDateTimesUntilNextYear(schedule.getRepeatType(), startDate,
                endDate, new ArrayList<>());

        List<Schedule> repeatSchedules = Schedule.of(dateTimesUntilNextYear, schedule, repeatId);

        repeatSchedules.forEach(repeatSchedule -> {
            scheduleRepository.save(repeatSchedule);
            List<UserSchedule> repeatedUserSchedules = mates.stream()
                    .map(user -> new UserSchedule(user, repeatSchedule))
                    .collect(Collectors.toList());
            repeatSchedule.getMates().addAll(repeatedUserSchedules);
        });
    }

    @Transactional
    public ScheduleResponse update(Long scheduleId, ScheduleUpdateRequest dto, boolean all, UserDetail userDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
        chkScheduleWriter(schedule.getWriter(), userDto.getUserId());

        List<User> newMates = validateMates(dto.mates());
        String repeatId = schedule.getRepeatId();

        if (repeatId == null) {// 단일 스케줄의 변경 건
            schedule.update(dto);
            updateUserSchedules(schedule, newMates);
            if (!dto.repeatType().equals(RepeatType.NONE)) {// 변경으로 반복 주기가 생기는 경우
                createRepeatSchedule(schedule, newMates);
            }
        } else {// repeatId를 변경하지 않는 반복스케줄 변경 요청인 경우
            schedule.updateRepeatedSchedule(dto);// alertType, memo 변경
            updateUserSchedules(schedule, newMates);// mates 변경
            if (all) {
                List<Schedule> repeatedSchedules = scheduleRepository.findAllByRepeatIdAndScheduleDateTimeAfter(
                        repeatId,
                        schedule.getScheduleDateTime()).get();
                repeatedSchedules.forEach(repeatSchedule -> {
                            repeatSchedule.updateRepeatedSchedule(dto);
                            updateUserSchedules(repeatSchedule, newMates);
                        }
                );
            }
        }
        return ScheduleResponse.of(schedule);
    }

    @Transactional // 반복스케줄의 변경으로 이전주기에 해당하는 반복스케줄 전부 삭제 + 요청 건으로 새로운 반복 스케줄 등록
    public ScheduleResponse replace(Long scheduleId, ScheduleUpdateRequest dto, UserDetail userDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));

        String repeatId = schedule.getRepeatId();
        List<User> newMates = validateMates(dto.mates());

        chkScheduleWriter(schedule.getWriter(), userDto.getUserId());

        List<Schedule> repeatedSchedules = scheduleRepository.findAllByRepeatIdAndScheduleDateTimeGreaterThanEqual(
                repeatId, schedule.getScheduleDateTime()).get();
        List<Long> scheduleIds = repeatedSchedules.stream()
                .map(perSchedules -> perSchedules.getId())
                .collect(Collectors.toList());

        scheduleRepository.deleteAll(repeatedSchedules);
        userScheduleRepository.deleteByScheduleIdIn(scheduleIds);

        Schedule newRepeatedSchedule = ScheduleUpdateRequest.fromDto(dto, schedule.getHomeId(), userDto.getUserId());
        scheduleRepository.save(newRepeatedSchedule);
        List<UserSchedule> userSchedules = UserSchedule.of(newMates, newRepeatedSchedule);
        newRepeatedSchedule.getMates().addAll(userSchedules);

        if (dto.repeatType() != null && !RepeatType.NONE.equals(dto.repeatType())) {
            createRepeatSchedule(newRepeatedSchedule, newMates);
        }

        return ScheduleResponse.of(newRepeatedSchedule);
    }

    @Transactional
    public void updateUserSchedules(Schedule schedule, List<User> newMates) {
        if (!isSameMates(schedule, newMates)) {// mate 구성이 달라진 경우
            List<UserSchedule> userSchedules = userScheduleRepository.findAllByScheduleId(schedule.getId())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
            userScheduleRepository.deleteAll(userSchedules);
            List<UserSchedule> newUserSchedules = UserSchedule.of(newMates, schedule);
            schedule.getMates().clear();
            schedule.getMates().addAll(newUserSchedules);// TODO :: userSchedule 생성 되었는지 확인
        }
    }

    @Transactional
    public void delete(Long scheduleId, boolean all, UserDetail userDto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
        chkScheduleWriter(schedule.getWriter(), userDto.getUserId());

        if (schedule.getRepeatId() == null) {
            scheduleRepository.delete(schedule);
        } else if (all) {
            List<Schedule> repeatedSchedules = scheduleRepository.findAllByRepeatIdAndScheduleDateTimeGreaterThanEqual(
                            schedule.getRepeatId(), schedule.getScheduleDateTime())
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
            scheduleRepository.deleteAll(repeatedSchedules);
        } else {
            scheduleRepository.delete(schedule);
        }
    }

    @Transactional
    public ScheduleResponse updateStatus(Long scheduleId, UserDetail userDto, boolean status) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_SCHEDULE));
        chkScheduleWriter(schedule.getWriter(), userDto.getUserId());

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

    private void chkScheduleWriter(Long requestedUserId, Long writerId) {
        if (requestedUserId != writerId) {
            throw new CustomException(ErrorCode.NOT_ACCESS_RESOURCE);
        }
    }

    private void chkScheduleMates(List<UserSchedule> mates, Long writerId) {
        mates.stream()
                .filter(userSchedule ->
                        userSchedule.getUser().getUserId() == writerId
                )
                .findAny()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_ACCESS_RESOURCE));
    }

    private boolean isSameMates(Schedule schedule, List<User> newMates) {
        List<Long> dtoMates = newMates.stream().map(User -> User.getUserId()).collect(Collectors.toList());
        return schedule.getMateIds().containsAll(dtoMates);
    }
}
