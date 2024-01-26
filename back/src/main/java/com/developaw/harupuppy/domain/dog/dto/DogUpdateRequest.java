package com.developaw.harupuppy.domain.dog.dto;

import com.developaw.harupuppy.domain.dog.domain.DogGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DogUpdateRequest (
        @NotBlank Long dogId,
        @NotBlank String name,
        @NotNull Double weight,
        @NotNull DogGender gender,
        @NotBlank String birthday,
        @NotBlank String imgUrl
){}
