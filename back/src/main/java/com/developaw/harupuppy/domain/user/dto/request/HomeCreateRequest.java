package com.developaw.harupuppy.domain.user.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HomeCreateRequest(
        @NotNull @Valid UserCreateRequest userRequest,
        @NotNull @Valid DogCreateRequest dogRequest,
        @NotBlank String homeName
) {
}
