package com.developaw.harupuppy.domain.user.dto.response;

import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.domain.UserRole;

public record UserDetailResponse(
        Long userId,
        String email,
        String imgUrl,
        String nickname,
        UserRole userRole,
        boolean allowNotification,
        Long dogId,
        String homeId

) {
    public static UserDetailResponse of(User user) {
        return new UserDetailResponse(
                user.getUserId(),
                user.getEmail(),
                user.getImgUrl(),
                user.getNickname(),
                user.getUserRole(),
                user.isAllowNotification(),
                user.getDog().getDogId(),
                user.getHome().getHomeId()
        );
    }
    public static UserDetailResponse of(UserDetail user) {
        return new UserDetailResponse(
                user.getUserId(),
                user.getEmail(),
                null,
                user.getNickName(),
                user.getUserRole(),
                user.isAllowNotification(),
                null,
                null
        );
    }
}
