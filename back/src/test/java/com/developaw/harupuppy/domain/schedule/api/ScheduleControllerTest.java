package com.developaw.harupuppy.domain.schedule.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.developaw.harupuppy.domain.schedule.application.ScheduleService;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleCreateRequest;
import com.developaw.harupuppy.fixture.ScheduleFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ScheduleService scheduleService;

    @Test
    @DisplayName("스케줄 생성")
    void create() throws Exception {
        ScheduleCreateRequest validDto = ScheduleFixture.getCreateDto();
        mvc.perform(
                        post("/api/schedules")
                                .content(objectMapper.writeValueAsString(validDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("스케줄 생성 시 필수값이 없어 유효성 검증에서 실패한다")
    void createWithInvalidDto() throws Exception {
        ScheduleCreateRequest invalidDto = ScheduleFixture.getCreateDtoWithNullType();
        MvcResult result = mvc.perform(
                        post("/api/schedules")
                                .content(objectMapper.writeValueAsString(invalidDto))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result.getResolvedException().getMessage().contains("메이트 지정이 필요합니다"));
    }
}
