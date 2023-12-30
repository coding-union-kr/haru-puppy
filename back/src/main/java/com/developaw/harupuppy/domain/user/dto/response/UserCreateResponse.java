package com.developaw.harupuppy.domain.user.dto.response;

public record UserCreateResponse(
        UserDetailResponse userResponse,
        HomeDetailResponse homeResponse,
        DogDetailResponse dogResponse
) {
    public static UserCreateResponse of(UserDetailResponse user, HomeDetailResponse home, DogDetailResponse dog){
        return new UserCreateResponse(user, home, dog);
    }
}
