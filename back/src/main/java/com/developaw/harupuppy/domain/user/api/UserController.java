package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.domain.user.dto.UserResponse;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PutMapping("/profile/{userId}")
    public ApiResponse<UserResponse> update (@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        return ApiResponse.ok(Response.Status.UPDATE, userService.updateUserInformation(userId, request));
    }
}
