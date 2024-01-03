package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.domain.user.dto.UserResponse;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    @PutMapping("/profile")
    public ApiResponse<UserResponse> updateProfile (@RequestBody @Valid UserUpdateRequest request){
        return ApiResponse.ok(Response.Status.UPDATE, userService.updateUserInformation(request));
    }
}
