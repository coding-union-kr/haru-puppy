package com.developaw.harupuppy.domain.user.dto.response;

public record OAuthLoginResponse(
        String email,
        boolean isAlreadyRegistered
) {
}
