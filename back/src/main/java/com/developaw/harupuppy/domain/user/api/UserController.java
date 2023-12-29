package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserDetailResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserCreateResponse> create(@RequestBody @Valid HomeCreateRequest request,
                                                  HttpServletResponse response){
        return ApiResponse.ok(Status.CREATE, userService.create(request, response));
    }

    @PostMapping("/invitation")
    public ApiResponse<UserDetailResponse> create(@RequestBody @Valid UserCreateRequest request){
        return ApiResponse.ok(Status.CREATE, userService.create(request));
    }
}
