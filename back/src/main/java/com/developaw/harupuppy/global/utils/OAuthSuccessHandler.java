package com.developaw.harupuppy.global.utils;

import com.developaw.harupuppy.domain.user.dto.TokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenUtils jwtTokenUtils;
    private static final String ACCESS_TOKEN = "Access-Token";
    private static final String REFRESH_TOKEN = "Refresh-Token";
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        TokenDto token = jwtTokenUtils.generateToken(authentication);
        response.setHeader(ACCESS_TOKEN, token.accessToken());
        response.setHeader(REFRESH_TOKEN, token.refreshToken());
    }
}
