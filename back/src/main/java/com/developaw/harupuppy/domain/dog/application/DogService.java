package com.developaw.harupuppy.domain.dog.application;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.dog.dto.DogUpdateRequest;
import com.developaw.harupuppy.domain.dog.repository.DogRepository;
import com.developaw.harupuppy.domain.user.dto.response.DogDetailResponse;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DogService {
    private final DogRepository dogRepository;

    @Transactional
    public DogDetailResponse updateDogInformation (DogUpdateRequest request) {
        Dog dog = dogRepository.findByDogId(request.dogId())
                .orElseThrow(() -> new CustomException(Response.ErrorCode.NOT_FOUND_DOG));
        dog.update(request);
        return DogDetailResponse.of(dog);
    }
}
