package com.developaw.harupuppy.domain.dog.dto;

import com.developaw.harupuppy.domain.dog.domain.DogGender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DogUpdateRequest(

        @NotNull(message = "고유번호는 필수항목입니다.")
        Long dogId,
        @NotBlank(message = "이름은 필수항목입니다.")
        String name,
        @NotBlank(message = "성별을 선택해주세요")
        DogGender gender,
        @NotNull(message = "생일을 입력해주세요")
        String birthday,
        @NotNull(message = "체중을 입력해주세요")
        Double weight
) {
}
