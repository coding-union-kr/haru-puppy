package com.developaw.harupuppy.domain.schedule.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.developaw.harupuppy.domain.schedule.dao.ScheduleRepository;
import com.developaw.harupuppy.domain.schedule.dao.UserScheduleRepository;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.fixture.ScheduleFixture;
import com.developaw.harupuppy.global.common.exception.CustomException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ScheduleServiceTest {
    static ScheduleCreateRequest createDto, repeatedDto, invalidDto;
    static List<User> mates;
    @InjectMocks
    private ScheduleService scheduleService;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private UserScheduleRepository userScheduleRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init() {
        createDto = ScheduleFixture.getCreateDto();
        repeatedDto = ScheduleFixture.getDailyRepeatedDto();
        invalidDto = ScheduleFixture.getCreateDtoWithInvalidDateType();
        mates = ScheduleFixture.getMates();
    }

    @Test
    @DisplayName("스케줄 정보를 받아 단일 스케줄을 생성한다")
    void create() {
        Schedule schedule = ScheduleCreateRequest.fromDto(createDto, null);
        List<UserSchedule> userSchedules = ScheduleFixture.getUserSchedules(mates, schedule);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(0)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(1)));
        when(scheduleRepository.save(any())).thenReturn(schedule);
        when(userScheduleRepository.saveAll(any())).thenReturn(userSchedules);

        assertThatNoException().isThrownBy(() -> scheduleService.create(createDto));
    }

    @ParameterizedTest
    @MethodSource("repeatedScheduleDate")
    @DisplayName("스케줄 정보를 받아 반복되는 스케줄을 생성한다")
    void createRepeatedSchedule(ScheduleCreateRequest repeatedDto, int expectedSchedulesCnt, int expectedUserSchedulesCnt) {
        String repeatId = "repeatId";
        Schedule schedule = ScheduleCreateRequest.fromDto(repeatedDto, repeatId);
        LocalDateTime startDate = schedule.getScheduleDateTime();
        LocalDateTime endDate = startDate.plusYears(1).withMonth(12).withDayOfMonth(31);

        List<LocalDateTime> dateTimesUntilNextYear = scheduleService.getDateTimesUntilNextYear(schedule.getRepeatType(),
                startDate, endDate, new ArrayList<>());
        List<Schedule> schedules = Schedule.of(dateTimesUntilNextYear, schedule, repeatId);
        List<UserSchedule> userSchedules = ScheduleFixture.getUserSchedules(mates, schedule);

        when(scheduleRepository.saveAll(any())).thenReturn(schedules);
        when(userScheduleRepository.saveAll(any())).thenReturn(userSchedules);

        assertThatNoException().isThrownBy(() -> scheduleService.createRepeatSchedule(schedule, mates));

        List<Schedule> savedSchedules = captureSaveAllInvocation();
        assertThat(savedSchedules).hasSize(expectedSchedulesCnt);

        List<UserSchedule> savedUserSchedules = captureSaveAllUserSchedulesInvocation();
        assertThat(savedUserSchedules).hasSize(expectedUserSchedulesCnt);
    }

    private static Stream<Arguments> repeatedScheduleDate() {// 반복 기준으로 insert되는 사이즈 확인
        return Stream.of(
                Arguments.of(ScheduleFixture.getDailyRepeatedDto(), 371, 742),// 일 반복
                Arguments.of(ScheduleFixture.getWeeklyRepeatedDto(), 53, 106),// 주 반복
                Arguments.of(ScheduleFixture.getMonthlyRepeatedDto(), 12, 24)// 월 반복
        );
    }


    @Test
    @DisplayName("스케줄 정보의 날짜, 시간 타입이 유효하지 않아 스케줄을 생성할 수 없다")
    void createWithInvalidDateType() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(0)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(1)));
        assertThatThrownBy(() -> scheduleService.create(invalidDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("스케줄 날짜와 시간이 유효하지 않습니다");

    }

    @Test
    @DisplayName("메이트 정보가 존재하지 않아 새로운 스케줄을 저장할 수 없다")
    void createWithNotFoundUser() {
        assertThatThrownBy(() -> scheduleService.create(createDto))
                .isInstanceOf(CustomException.class)
                .hasMessage("가입된 유저가 아닙니다");
    }

    private List<Schedule> captureSaveAllInvocation() {
        ArgumentCaptor<List<Schedule>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(scheduleRepository).saveAll(argumentCaptor.capture());
        return argumentCaptor.getValue();
    }

    private List<UserSchedule> captureSaveAllUserSchedulesInvocation() {
        ArgumentCaptor<List<UserSchedule>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(userScheduleRepository).saveAll(argumentCaptor.capture());
        return argumentCaptor.getValue();
    }


}