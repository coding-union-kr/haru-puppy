package com.developaw.harupuppy.domain.schedule.api;

import com.developaw.harupuppy.domain.schedule.application.ScheduleService;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateDto;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    //TODO :: SpringContextHolder에 저장된 UserDto를 컨트롤러로 가져와 service로 보내기
    @PostMapping
    public ApiResponse<Void> create(@RequestBody @Valid ScheduleCreateDto dto) {
        scheduleService.create(dto);
        return ApiResponse.ok(Status.CREATE, null);
    }
}
