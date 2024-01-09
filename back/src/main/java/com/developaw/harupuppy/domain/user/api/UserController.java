package com.developaw.harupuppy.domain.user.api;


import com.developaw.harupuppy.domain.user.application.UserFacadeService;
import com.developaw.harupuppy.domain.user.application.UserService;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserDetailResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserFacadeService facadeService;
    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserCreateResponse> create(@NotNull @RequestBody @Valid HomeCreateRequest request) {
        return ApiResponse.ok(Status.CREATE, facadeService.create(request));
    }
    
    @PutMapping("/profile")
    public ApiResponse<UserDetailResponse> updateProfile (@RequestBody @Valid UserUpdateRequest request){
        return ApiResponse.ok(Response.Status.UPDATE, userService.updateUserInformation(request));
    }

    @PostMapping("/invitation/{homeId}")
    public ApiResponse<UserCreateResponse> create(@NotNull @RequestBody @Valid UserCreateRequest request,
                                                  @NotBlank @PathVariable("homeId") String homeId) {
        return ApiResponse.ok(Status.CREATE, facadeService.create(request, homeId));
    }
}
