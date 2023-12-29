package com.developaw.harupuppy.domain.user.api;

import com.developaw.harupuppy.domain.user.application.OAuthService;
import com.developaw.harupuppy.domain.user.application.UserFacadeService;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response.Status;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final OAuthService oAuthService;
    private final UserFacadeService userFacadeService;

    @GetMapping("/login/{provider}")
    public ApiResponse<String> login(@PathVariable("provider") String provider,
                                               @RequestParam("code") String code,
                                               HttpServletResponse response) {
        return ApiResponse.ok(Status.CREATE, userFacadeService.login(provider, code, response));
    }
}
