package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.UserFacadeService;
import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.dto.UserResponse;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@CrossOrigin(originPatterns = "http://localhost:3000", maxAge = 3600)
public class UserController {
    private final UserFacadeService facadeService;
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserCreateResponse> create(@NotNull @RequestBody @Valid HomeCreateRequest request) {
        return ApiResponse.ok(Status.CREATE, facadeService.create(request));
    }

    @PutMapping("/profile")
    public ApiResponse<UserResponse> updateProfile(@RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.ok(Status.UPDATE, userService.updateUserInformation(request));
    }

    @PostMapping("/invitation/{homeId}")
    public ApiResponse<UserCreateResponse> create(@NotNull @RequestBody @Valid UserCreateRequest request,
                                                  @NotBlank @PathVariable("homeId") String homeId) {
        return ApiResponse.ok(Status.CREATE, facadeService.create(request, homeId));
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Void> delete(@PathVariable("userId") Long userId,
                                    @AuthenticationPrincipal UserDetail requestUser) {
        facadeService.delete(userId, requestUser);
        return ApiResponse.ok(Status.DELETE);
    }
}
