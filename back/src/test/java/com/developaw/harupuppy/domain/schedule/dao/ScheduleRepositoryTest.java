package com.developaw.harupuppy.domain.schedule.dao;

import com.developaw.harupuppy.domain.schedule.domain.Schedule;
import com.developaw.harupuppy.domain.schedule.domain.ScheduleType;
import com.developaw.harupuppy.domain.schedule.domain.UserSchedule;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.fixture.ScheduleFixture;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ScheduleRepositoryTest {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserScheduleRepository userScheduleRepository;

    @AfterEach
    void tearDown() {
        userScheduleRepository.deleteAllInBatch();
        scheduleRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    void save() {
        ScheduleCreateRequest createDto = ScheduleFixture.getCreateDto("2023-12-25");
        List<User> mates = ScheduleFixture.getMates();
        userRepository.saveAll(mates);

        Schedule savedSchedule = scheduleRepository.save(ScheduleCreateRequest.fromDto(createDto, "homeId"));

        List<UserSchedule> userSchedules = ScheduleFixture.getUserSchedules(mates, savedSchedule);
        userScheduleRepository.saveAll(userSchedules);

        UserSchedule foundedUserSchedule = userScheduleRepository.findById(userSchedules.get(0).getUserSchedulePK())
                .orElse(null);

        Assertions.assertEquals(savedSchedule.getScheduleType(), ScheduleType.POO);
        Assertions.assertNotNull(foundedUserSchedule);
    }
}
