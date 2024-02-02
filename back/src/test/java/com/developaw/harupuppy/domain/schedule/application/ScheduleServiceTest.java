package com.developaw.harupuppy.domain.schedule.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.developaw.harupuppy.domain.schedule.dao.ScheduleRepository;
import com.developaw.harupuppy.domain.schedule.dao.UserScheduleRepository;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.developaw.harupuppy.domain.schedule.dto.response.ScheduleResponse;
import com.developaw.harupuppy.domain.user.domain.Home;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.repository.HomeRepository;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.fixture.ScheduleFixture;
import com.developaw.harupuppy.fixture.UserFixture;
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
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@Slf4j
class ScheduleServiceTest {
    static ScheduleCreateRequest createDto, repeatedDto, invalidDto;
    static ScheduleUpdateRequest updateDto;
    static List<User> mates;
    static UserDetail userDto;
    static HomeCreateRequest homeDto;
    static
    @InjectMocks
    private ScheduleService scheduleService;
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private UserScheduleRepository userScheduleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HomeRepository homeRepository;

    @BeforeEach
    void init() {
        createDto = ScheduleFixture.getCreateDto("2023-12-25");
        repeatedDto = ScheduleFixture.getDailyRepeatedDto();
        invalidDto = ScheduleFixture.getCreateDtoWithInvalidDateType();
        updateDto = ScheduleFixture.getUpdateDto();
        mates = ScheduleFixture.getMates();
        userDto = ScheduleFixture.getUserDto();
        homeDto = UserFixture.getHomeRequest();
    }

    @Test
    @DisplayName("스케줄 정보를 받아 단일 스케줄을 생성한다")
    void create() {
        Home home = HomeCreateRequest.fromDto(homeDto);
        Schedule schedule = ScheduleCreateRequest.fromDto(createDto, "homeId", 1L);
        List<UserSchedule> userSchedules = ScheduleFixture.getUserSchedules(mates, schedule);
        when(homeRepository.findByHomeId(anyString())).thenReturn(Optional.of(home));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(0)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(1)));
        when(scheduleRepository.save(any())).thenReturn(schedule);
        when(userScheduleRepository.saveAll(any())).thenReturn(userSchedules);

        ScheduleResponse response = scheduleService.create(createDto, userDto);
        assertThat(response.scheduleDateTime()).isEqualTo(schedule.getScheduleDateTime());
        assertThat(response.mates().get(0).userId())
                .isEqualTo(userSchedules.get(0).getUser().getUserId());
    }

    @ParameterizedTest
    @MethodSource("repeatedScheduleDate")
    @DisplayName("스케줄 정보를 받아 반복되는 스케줄을 생성한다")
    void createRepeatedSchedule(ScheduleCreateRequest repeatedDto, int expectedSchedulesCnt,
                                int expectedUserSchedulesCnt) {
        String repeatId = "repeatId";
        Schedule schedule = ScheduleCreateRequest.fromDto(repeatedDto, repeatId, 1L);
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
        assertThatThrownBy(() -> scheduleService.create(invalidDto, userDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("스케줄 날짜와 시간이 유효하지 않습니다");

    }

    @Test
    @DisplayName("메이트 정보가 존재하지 않아 새로운 스케줄을 저장할 수 없다")
    void createWithNotFoundUser() {
        assertThatThrownBy(() -> scheduleService.create(createDto, userDto))
                .isInstanceOf(CustomException.class)
                .hasMessage("가입된 유저가 아닙니다");
    }

    @Test
    @DisplayName("요청된 내용으로 스케줄을 수정한다")
    void updateSchedule() {
        Long scheduleId = 1L;
        boolean all = false;
        Schedule schedule = ScheduleCreateRequest.fromDto(repeatedDto, "repeatId", 1L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(0)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(1)));
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        ScheduleResponse response = scheduleService.update(scheduleId, updateDto, all, userDto);

        assertThat(response.memo()).isEqualTo(updateDto.memo());
        assertThat(response.repeatType()).isEqualTo(updateDto.repeatType());
    }

    @Test
    @DisplayName("요청된 내용으로 해당 스케줄과 반복된 스케줄들의 내용을 전부 수정한다")
    void updateRepeatSchedule() {
        Schedule rawSchedule = ScheduleCreateRequest.fromDto(ScheduleFixture.getMonthlyRepeatedDto(), "homeId", 1L);
        ReflectionTestUtils.setField(rawSchedule, "id", 1L);
        List<Schedule> repeatSchedules = ScheduleFixture.getRepeatSchedules(rawSchedule);
        boolean all = true;

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(0)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(1)));
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(rawSchedule));
        when(scheduleRepository.findAllByRepeatIdAndScheduleDateTimeAfter(anyString(),
                any())).thenReturn(Optional.of(repeatSchedules));
        ScheduleResponse response = scheduleService.update(1L, updateDto, all, userDto);

        assertThat(response.alertType()).isEqualTo(rawSchedule.getAlertType());
    }

    @Test
    @DisplayName("요청한 스케줄 아이디로 스케줄을 삭제한다")
    void deleteSchedule() {
        Schedule schedule = ScheduleCreateRequest.fromDto(createDto, "homeId", 1L);
        ReflectionTestUtils.setField(schedule, "id", 1L);
        boolean all = false;

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));

        assertThatNoException().isThrownBy(() -> scheduleService.delete(1L, all, userDto));
    }

    @Test
    @DisplayName("요청한 스케줄 아이디로 반복되는 스케줄까지 전부 삭제한다")
    void deleteRepeatedSchedule() {
        Schedule schedule = ScheduleCreateRequest.fromDto(repeatedDto, "homeId", "repeatId", 1L);
        List<Schedule> repeatSchedules = ScheduleFixture.getRepeatSchedules(schedule);
        ReflectionTestUtils.setField(schedule, "repeatId", "repeatId");
        ReflectionTestUtils.setField(schedule, "id", 1L);
        boolean all = true;

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
        when(scheduleRepository.findAllByRepeatIdAndScheduleDateTimeAfter(
                anyString(), any())).thenReturn(Optional.of(repeatSchedules));
        assertThatNoException().isThrownBy(() -> scheduleService.delete(1L, all, userDto));
    }

    @Test
    @DisplayName("스케줄 아이디에 해당하는 스케줄을 찾아 반환한다")
    void get() {
        Schedule schedule = ScheduleCreateRequest.fromDto(createDto, "homeId", 1L);
        ReflectionTestUtils.setField(schedule, "id", 1L);
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));

        ScheduleResponse response = scheduleService.get(schedule.getId());
        assertThat(response.scheduleId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("특정 연도, 월에 해당하는 모든 스케줄을 찾아 반환한다")
    void getSchedules() {
        List<Schedule> scheduleList = ScheduleFixture.getSchedulesWithMonth();

        when(scheduleRepository.findAllByHomeIdAndScheduleDateTimeBetweenOrderByScheduleDateTimeAsc(any(), any(), any()))
                .thenReturn(Optional.of(scheduleList));

        List<ScheduleResponse> response = scheduleService.getSchedules("homeId", 2024, 1);
        assertThat(response.get(0).scheduleDateTime()).isEqualTo(LocalDateTime.of(2024, 1, 2, 12, 35));
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