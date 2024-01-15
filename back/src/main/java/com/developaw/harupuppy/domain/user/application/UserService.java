package com.developaw.harupuppy.domain.user.application;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.dog.repository.DogRepository;
import com.developaw.harupuppy.domain.user.domain.Home;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
import com.developaw.harupuppy.domain.user.dto.UserResponse;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import com.developaw.harupuppy.domain.user.dto.request.DogCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import com.developaw.harupuppy.domain.user.dto.response.DogDetailResponse;
import com.developaw.harupuppy.domain.user.dto.response.HomeDetailResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserCreateResponse;
import com.developaw.harupuppy.domain.user.dto.response.UserDetailResponse;
import com.developaw.harupuppy.domain.user.repository.HomeRepository;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response;
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;
    private final DogRepository dogRepository;

    @Transactional
    public UserCreateResponse create(HomeCreateRequest request) {
        Dog dog = DogCreateRequest.fromDto(request.dogRequest());
        DogDetailResponse dogDetail = DogDetailResponse.of(dogRepository.save(dog));

        Home home = Home.builder()
                .dog(dog)
                .homeName(request.homeName())
                .build();
        HomeDetailResponse homeDetail = HomeDetailResponse.of(homeRepository.save(home));

        User user = UserCreateRequest.fromDto(request.userRequest(), home, dog);
        UserDetailResponse userDetail = UserDetailResponse.of(userRepository.save(user));

        return UserCreateResponse.of(userDetail, homeDetail, dogDetail, null);
    }

    @Transactional
    public UserCreateResponse create(UserCreateRequest request, String homeId) {
        Home home = homeRepository.findByHomeId(homeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOME));
        Dog dog = home.getDog();
        User invitedUser = UserCreateRequest.fromDto(request, home, dog);
        userRepository.save(invitedUser);
        return UserCreateResponse.of(UserDetailResponse.of(invitedUser), HomeDetailResponse.of(home),
                DogDetailResponse.of(dog), null);
    }

    @Transactional
    public String withdraw(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        userRepository.delete(user);
        return user.getEmail();
    }

    @Transactional
    public UserDetail loadByUserId(Long userId) {
        User registedUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return UserDetail.of(registedUser);
    }

    @Transactional
    public UserResponse updateUserInformation(UserUpdateRequest request) {
        User user = userRepository.findUserByUserId(request.userId())
                .orElseThrow(() -> new CustomException(Response.ErrorCode.NOT_FOUND_USER));
        user.update(request);
        return UserResponse.of(user);
    }
}

