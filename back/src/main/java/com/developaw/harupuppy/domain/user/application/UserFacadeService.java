package com.developaw.harupuppy.domain.user.application;

import com.developaw.harupuppy.domain.user.dto.TokenDto;
import com.developaw.harupuppy.domain.user.dto.response.LoginResponse;
import com.developaw.harupuppy.global.utils.JwtTokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeService {
    static final String ACCESS_TOKEN = "Access-Token";
    static final String REFRESH_TOKEN = "Refresh-Token";
    @Value("${jwt.refresh-expired-time-ms}")
    private Long refreshExpiredTimeMs;
    private final OAuthService oAuthService;
    private final JwtTokenUtils jwtTokenUtils;
    private final RedisService redisService;

    public String login(String provider, String code, HttpServletResponse response) {
        LoginResponse user = oAuthService.login(provider, code);
        String email = user.email();
        if (user.isAlreadyRegistered()) {
            TokenDto token = jwtTokenUtils.generateToken(email);
            response.setHeader(ACCESS_TOKEN, token.accessToken());
            response.setHeader(REFRESH_TOKEN, token.refreshToken());
            redisService.setValue(email, token.refreshToken(), Duration.ofMillis(refreshExpiredTimeMs));
        }
        return user.email();
    }
}
