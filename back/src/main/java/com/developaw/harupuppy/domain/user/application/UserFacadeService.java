package com.developaw.harupuppy.domain.user.application;

import com.developaw.harupuppy.domain.user.dto.TokenDto;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.LoginResponse;
import com.developaw.harupuppy.domain.user.dto.response.OAuthLoginResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserDetailResponse;
import com.developaw.harupuppy.global.utils.JwtTokenUtils;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserFacadeService {
    @Value("${jwt.refresh-expired-time-ms}")
    private Long refreshExpiredTimeMs;
    private final OAuthService oAuthService;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final RedisService redisService;

    @Transactional
    public LoginResponse login(String provider, String code) {
        OAuthLoginResponse response = oAuthService.login(provider, code);
        String email = response.email();
        if (response.isAlreadyRegistered()) {
            TokenDto token = createTokenAndSave(response.registeredUser(), email);
            return new LoginResponse(response, token.accessToken(), token.refreshToken());
        }
        return LoginResponse.of(response);
    }

    @Transactional
    public UserCreateResponse create(HomeCreateRequest request) {
        UserCreateResponse userDetail = userService.create(request);
        String email = userDetail.getUserResponse().email();
        TokenDto token = createTokenAndSave(userDetail.getUserResponse(), email);
        userDetail.setToken(token);
        return userDetail;
    }

    @Transactional
    public UserCreateResponse create(UserCreateRequest request, String homeId) {
        UserCreateResponse userDetail = userService.create(request, homeId);
        String email = userDetail.getUserResponse().email();
        TokenDto token = createTokenAndSave(userDetail.getUserResponse(), email);
        userDetail.setToken(token);
        return userDetail;
    }

    @Transactional
    public TokenDto createTokenAndSave(UserDetailResponse response, String email) {
        TokenDto token = jwtTokenUtils.generateToken(response);
        redisService.setValue(token.refreshToken(), email, Duration.ofMillis(refreshExpiredTimeMs));
        return token;
    }
}
