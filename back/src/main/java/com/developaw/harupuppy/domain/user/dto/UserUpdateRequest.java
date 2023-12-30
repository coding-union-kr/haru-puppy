package com.developaw.harupuppy.domain.user.dto;

import com.developaw.harupuppy.domain.user.domain.UserRole;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        Long userId,
        String userImg,
        @NotBlank(message = "닉네임을 입력해주세요.") String nickname,
        UserRole userRole
) {
}
