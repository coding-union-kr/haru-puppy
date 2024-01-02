package com.developaw.harupuppy.domain.user.dto.response;

public record LoginResponse(
        OAuthLoginResponse response,
        String accessToken,
        String refreshToken
) {
    public static LoginResponse of(OAuthLoginResponse response){
        return new LoginResponse(response, null, null);
    }
}
