package com.developaw.harupuppy.domain.schedule.api;

import com.developaw.harupuppy.domain.schedule.application.ScheduleService;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.developaw.harupuppy.domain.schedule.dto.response.ScheduleResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
@Validated
public class ScheduleController {
    private final ScheduleService scheduleService;

    //TODO :: SpringContextHolder에 저장된 UserDto를 컨트롤러로 가져와 service로 보내기
    @PostMapping
    public ApiResponse<ScheduleResponse> create(@RequestBody ScheduleCreateRequest dto) {
        return ApiResponse.ok(Status.CREATE, scheduleService.create(dto));
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponse> update(@NotNull @PathVariable Long scheduleId,
                                                @NotNull @RequestBody ScheduleUpdateRequest dto,
                                                @NotNull @RequestParam Boolean all) {
        return ApiResponse.ok(Status.UPDATE, scheduleService.update(scheduleId, dto, all));
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> delete(@NotNull @RequestBody Long scheduleId,
                                    @NotNull @RequestParam Boolean all) {
        scheduleService.delete(scheduleId, all);
        return ApiResponse.ok(Status.DELETE);
    }

    @PutMapping("/{scheduleId}/status")
    public ApiResponse<ScheduleResponse> updateStatus(@NotNull @RequestParam Boolean active,
                                                      @NotNull @PathVariable Long scheduleId) {
        return ApiResponse.ok(Status.UPDATE, scheduleService.updateStatus(scheduleId, active));
    }
}
