package com.developaw.harupuppy.domain.user.dto.response;

import com.developaw.harupuppy.domain.user.domain.Home;

public record HomeDetailResponse(
        String homeId,
        String homeName
) {
    public static HomeDetailResponse of(Home home){
        return new HomeDetailResponse(
                home.getHomeId(),
                home.getHomeName());
    }
}
