package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.UserFacadeService;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserFacadeService facadeService;

    @PostMapping("/register")
    public ApiResponse<UserCreateResponse> create(@NotNull @RequestBody @Valid HomeCreateRequest request,
                                                  HttpServletResponse response) {
        return ApiResponse.ok(Status.CREATE, facadeService.create(request, response));
    }

    @PostMapping("/invitation/{homeId}")
    public ApiResponse<UserCreateResponse> create(@NotNull @RequestBody @Valid UserCreateRequest request,
                                                  @NotBlank @PathVariable("homeId") String homeId,
                                                  HttpServletResponse response) {
        return ApiResponse.ok(Status.CREATE, facadeService.create(request, homeId, response));
    }
}
