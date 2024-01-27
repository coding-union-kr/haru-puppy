package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.UserFacadeService;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.dto.TokenDto;
import com.developaw.harupuppy.domain.user.dto.response.LoginResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
@CrossOrigin(origins = "*", exposedHeaders = {"Content-Disposition"})
public class AuthController {
    private final UserFacadeService userFacadeService;

    @GetMapping("/login/{provider}")
    public ApiResponse<LoginResponse> login(@PathVariable("provider") String provider,
                                            @RequestParam("code") String code) {
        LoginResponse response = userFacadeService.login(provider, code);
        return ApiResponse.ok(Status.CREATE, response);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = Objects.requireNonNull(authentication.getCredentials()).toString();
        userFacadeService.logout(accessToken);
        return ApiResponse.ok(Status.CREATE);
    }

    @PostMapping("/refresh")
    public ApiResponse<TokenDto> refreshToken(@AuthenticationPrincipal UserDetail userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String refreshToken = Objects.requireNonNull(authentication.getCredentials()).toString();
        return ApiResponse.ok(Status.CREATE, userFacadeService.refreshToken(refreshToken, userDto));
    }
}
