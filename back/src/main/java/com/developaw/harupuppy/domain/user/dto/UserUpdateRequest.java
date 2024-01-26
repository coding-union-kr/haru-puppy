package com.developaw.harupuppy.domain.user.dto;

import com.developaw.harupuppy.domain.user.domain.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequest(
        @NotNull(message = "유저번호는 필수항목입니다.")
        Long userId,
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,
        @NotNull(message = "역할을 입력해주세요.")
        UserRole userRole
) {
}