package com.developaw.harupuppy.domain.user.dto.response;

import com.developaw.harupuppy.domain.user.dto.TokenDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCreateResponse{
    private UserDetailResponse userResponse;
    private HomeDetailResponse homeResponse;
    private DogDetailResponse dogResponse;
    private TokenDto token;

    @Builder
    public UserCreateResponse(UserDetailResponse userResponse, HomeDetailResponse homeResponse,
                              DogDetailResponse dogResponse, TokenDto token) {
        this.userResponse = userResponse;
        this.homeResponse = homeResponse;
        this.dogResponse = dogResponse;
        this.token = token;
    }

    public static UserCreateResponse of(UserDetailResponse user, HomeDetailResponse home, DogDetailResponse dog,
                                        TokenDto token) {
        return new UserCreateResponse(user, home, dog, token);
    }

    public void setToken(final TokenDto token){
        this.token = token;
    }
}
