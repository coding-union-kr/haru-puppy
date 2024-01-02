package com.developaw.harupuppy.domain.user.dto.response;

import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.domain.UserRole;

public record UserDetailResponse(
        Long userId,

        String email,

        String nickName,

        UserRole userRole,

        boolean isDeleted,

        boolean allowNotification
) {
    public static UserDetailResponse of(User user){
        return new UserDetailResponse(
                user.getUserId(),
                user.getEmail(),
                user.getNickname(),
                user.getUserRole(),
                user.isDeleted(),
                user.isAllowNotification()
        );
    }
}
