package com.developaw.harupuppy.domain.dog.application;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.dog.dto.DogUpdateRequest;
import com.developaw.harupuppy.domain.dog.repository.DogRepository;
import com.developaw.harupuppy.domain.user.dto.response.DogDetailResponse;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DogService {
    private final DogRepository dogRepository;

    @Transactional
    public DogDetailResponse updateDogInformation (Long dogId, @Valid DogUpdateRequest request){
        Dog dog = dogRepository.findDogByDogId(dogId)
                .orElseThrow(() -> new CustomException(Response.ErrorCode.NOT_FOUND_DOG));
        dog.update(request);
        return DogDetailResponse.of(dog);
    }
}
