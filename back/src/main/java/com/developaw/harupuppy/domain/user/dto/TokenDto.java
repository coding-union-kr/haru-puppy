package com.developaw.harupuppy.domain.user.dto;

public record TokenDto(
        String tokenKey,
        String accessToken,
        String refreshToken
) {
}
