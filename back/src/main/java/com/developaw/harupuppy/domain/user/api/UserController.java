package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.UserFacadeService;
import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.dto.UserResponse;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserDetailResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    public ApiResponse<UserDetailResponse> updateProfile(@RequestBody @Valid UserUpdateRequest request, HttpServletRequest httpReq) {
        return ApiResponse.ok(Status.UPDATE, userService.updateUserInformation(request, httpReq));
    }

    @PostMapping("/invitation/{homeId}")
    public ApiResponse<UserCreateResponse> create(@NotNull @RequestBody @Valid UserCreateRequest request,
                                                  @NotBlank @PathVariable("homeId") String homeId) {
        return ApiResponse.ok(Status.CREATE, facadeService.create(request, homeId));
    }

    @PostMapping("/withdraw")
    public ApiResponse<Void> withdraw(@AuthenticationPrincipal UserDetail requestUser) {
        facadeService.withdraw(requestUser);
        return ApiResponse.ok(Status.DELETE);
    }
}
