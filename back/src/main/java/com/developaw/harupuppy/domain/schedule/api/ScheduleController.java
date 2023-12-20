package com.developaw.harupuppy.domain.schedule.api;

import com.developaw.harupuppy.domain.schedule.application.ScheduleService;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleCreateRequest;
import com.developaw.harupuppy.domain.schedule.dto.request.ScheduleUpdateRequest;
import com.developaw.harupuppy.domain.schedule.dto.response.ScheduleResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ApiResponse<ScheduleResponse> create(@RequestBody @Valid ScheduleCreateRequest dto) {
        return ApiResponse.ok(Status.CREATE, scheduleService.create(dto));
    }

    @PutMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponse> update(@NotNull(message = "스케줄 아이디는 필수입니다") @PathVariable Long scheduleId,
                                                @NotNull @RequestBody @Valid ScheduleUpdateRequest dto,
                                                @NotNull(message = "반복 스케줄 수정여부는 필수입니다") @RequestParam(defaultValue = "false") Boolean all) {
        return ApiResponse.ok(Status.UPDATE, scheduleService.update(scheduleId, dto, all));
    }

    @DeleteMapping("/{scheduleId}")
    public ApiResponse<Void> delete(@NotNull(message = "스케줄 아이디는 필수입니다") @PathVariable Long scheduleId,
                                    @NotNull @RequestParam(defaultValue = "false") Boolean all) {
        scheduleService.delete(scheduleId, all);
        return ApiResponse.ok(Status.DELETE);
    }

    @PutMapping("/{scheduleId}/status")
    public ApiResponse<ScheduleResponse> updateStatus(@NotNull(message = "스케줄 상태는 필수입니다") @RequestParam Boolean active,
                                                      @NotNull(message = "스케줄 아이디는 필수입니다") @PathVariable Long scheduleId) {
        return ApiResponse.ok(Status.UPDATE, scheduleService.updateStatus(scheduleId, active));
    }

    @GetMapping("/{scheduleId}")
    public ApiResponse<ScheduleResponse> get(@NotNull(message = "스케줄 아이디는 필수입니다") @PathVariable Long scheduleId) {
        return ApiResponse.ok(Status.RETRIEVE, scheduleService.get(scheduleId));
    }

    @GetMapping("/home/{homeId}")
    public ApiResponse<List<ScheduleResponse>> getSchedules(
            @NotNull(message = "조회 연도는 필수입니다") @RequestParam("year") Integer year,
            @NotNull(message = "조회 월은 필수입니다") @RequestParam("month") Integer month,
            @NotBlank(message = "홈 아이디는 필수입니다") @PathVariable String homeId) {
        return ApiResponse.ok(Status.RETRIEVE, scheduleService.getSchedules(homeId, year, month));
    }
}
