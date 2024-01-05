package com.developaw.harupuppy.domain.user.dto;

public record TokenDto(
        String accessToken,
        String refreshToken
) {
}
