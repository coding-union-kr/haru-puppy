package com.developaw.harupuppy.domain.schedule.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.developaw.harupuppy.domain.schedule.dao.ScheduleRepository;
import com.developaw.harupuppy.domain.schedule.dao.UserScheduleRepository;
import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateDto;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.fixture.ScheduleFixture;
import com.developaw.harupuppy.global.common.exception.CustomException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {
    static ScheduleCreateDto createDto;
    static ScheduleCreateDto invalidDto;
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
        invalidDto = ScheduleFixture.getCreateDtoWithInvalidDateType();
        mates = ScheduleFixture.getMates();
    }

    @Test
    @DisplayName("스케줄 정보를 받아 새로운 스케줄을 생성한다")
    void create() {
        Schedule schedule = ScheduleCreateDto.fromDto(createDto);
        List<UserSchedule> userSchedules = ScheduleFixture.getUserSchedules(mates, schedule);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(0)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(1)));
        when(scheduleRepository.save(any())).thenReturn(schedule);
        when(userScheduleRepository.saveAll(any())).thenReturn(userSchedules);

        assertThatNoException().isThrownBy(() -> scheduleService.create(createDto));}
    @Test
    @DisplayName("스케줄 정보의 날짜, 시간 타입이 유효하지 않아 스케줄을 생성할 수 없다")
    void createWithInvalidDateType(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(0)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mates.get(1)));
        assertThatThrownBy( () -> scheduleService.create(invalidDto))
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
}