package com.developaw.harupuppy.domain.user.application;

import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.dto.TokenDto;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.LoginResponse;
import com.developaw.harupuppy.domain.user.dto.response.OAuthLoginResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserDetailResponse;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
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

    @Transactional
    public void delete(Long userId, UserDetail requestUser){
        if(userId != requestUser.getUserId()){
            throw new CustomException(ErrorCode.NOT_ACCESS_RESOURCE);
        }
        String email = userService.delete(userId);
        redisService.deleteValue(email);
    }

    @Transactional
    public TokenDto reissue(String refreshToken){
        refreshToken = validateToken(refreshToken);
        Long userId = jwtTokenUtils.resolveUserId(refreshToken);
        String key = jwtTokenUtils.resolveTokenKey(refreshToken);
        log.info("resolve Token : %d and %s", userId, key);

        if(!redisService.getValues(key).equals(refreshToken)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        UserDetail userDetail = userService.loadByUserId(userId);
        TokenDto newToken = jwtTokenUtils.generateToken(UserDetailResponse.of(userDetail));
        redisService.setValue(newToken.tokenKey(), newToken.refreshToken(), Duration.ofMillis(refreshExpiredTimeMs));
        redisService.deleteValue(key);
        return newToken;
    }

    private String validateToken(String refreshToken){
        if(!refreshToken.startsWith("Bearer ")){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }else if(jwtTokenUtils.isExpired(refreshToken)){
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }else{
            return refreshToken.split(" ")[1];
        }
    }
}
