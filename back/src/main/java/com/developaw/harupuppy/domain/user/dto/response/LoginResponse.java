package com.developaw.harupuppy.domain.user.dto.response;

public record LoginResponse(
        String email,
        boolean isAlreadyRegistered
) {
}
