package com.developaw.harupuppy.domain.dog.api;

import com.developaw.harupuppy.domain.dog.application.DogService;
import com.developaw.harupuppy.domain.dog.dto.DogUpdateRequest;
import com.developaw.harupuppy.domain.user.dto.response.DogDetailResponse;
import com.developaw.harupuppy.global.common.response.ApiResponse;
import com.developaw.harupuppy.global.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home/dogs")
@RequiredArgsConstructor
@Validated
public class DogController {
    private final DogService dogService;

    @PutMapping("/profile")
    public ApiResponse<DogDetailResponse> updateProfile (@RequestBody @Valid DogUpdateRequest request) {
        return ApiResponse.ok(Response.Status.UPDATE, dogService.updateDogInformation((request)));
    }
}
