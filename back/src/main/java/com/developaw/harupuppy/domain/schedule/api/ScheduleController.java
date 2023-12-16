package com.developaw.harupuppy.domain.schedule.api;

import com.developaw.harupuppy.domain.schedule.application.ScheduleService;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleDeleteRequest;
import com.developaw.harupuppy.domain.schedule.dto.ScheduleUpdateRequest;
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
    public ApiResponse<Void> create(@RequestBody @Validated ScheduleCreateRequest dto) {
        scheduleService.create(dto);
        return ApiResponse.ok(Status.CREATE, null);
    }

    @PutMapping
    public ApiResponse<Void> update(@RequestBody @Validated ScheduleUpdateRequest dto) {
        scheduleService.update(dto);
        return ApiResponse.ok(Status.UPDATE, null);
    }

    @DeleteMapping
    public ApiResponse<Void> delete(@RequestBody @Validated ScheduleDeleteRequest dto){
        scheduleService.delete(dto);
        return ApiResponse.ok(Status.DELETE, null);
    }

    @PutMapping("/{scheduleId}/status")
    public ApiResponse<Void> updateStatus(@NotNull @RequestParam Boolean active,
                                    @NotNull @PathVariable Long scheduleId){
        scheduleService.updateStatus(scheduleId, active);
        return ApiResponse.ok(Status.UPDATE, null);
    }
}
