package com.developaw.harupuppy.domain.user.dto.request;

import com.developaw.harupuppy.domain.user.domain.Home;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HomeCreateRequest(
        @NotNull @Valid UserCreateRequest userRequest,
        @NotNull @Valid DogCreateRequest dogRequest,
        @NotBlank String homeName
) {
    public static Home fromDto(HomeCreateRequest request) {
        return Home.builder()
                .dog(DogCreateRequest.fromDto(request.dogRequest))
                .homeName(request.homeName)
                .build();
    }
}
