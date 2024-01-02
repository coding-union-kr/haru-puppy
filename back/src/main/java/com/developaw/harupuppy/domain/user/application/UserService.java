package com.developaw.harupuppy.domain.user.application;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.dog.repository.DogRepository;
import com.developaw.harupuppy.domain.user.domain.Home;
import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.domain.UserDetail;
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
import com.developaw.harupuppy.global.common.response.Response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final HomeRepository homeRepository;
    private final DogRepository dogRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public UserCreateResponse create(HomeCreateRequest request) {
        Dog dog = DogCreateRequest.fromDto(request.dogRequest());
        DogDetailResponse dogDetail = DogDetailResponse.of(dogRepository.save(dog));

        Home home = Home.builder()
                .dog(dog)
                .homeName(request.homeName())
                .build();
        HomeDetailResponse homeDetail = HomeDetailResponse.of(homeRepository.save(home));

        User user = UserCreateRequest.fromDto(request.userRequest(),
                encoder.encode(request.userRequest().password()), home, dog);
        UserDetailResponse userDetail = UserDetailResponse.of(userRepository.save(user));

        return UserCreateResponse.of(userDetail, homeDetail, dogDetail, null);
    }

    @Transactional
    public UserCreateResponse create(UserCreateRequest request, String homeId) {
        Home home = homeRepository.findByHomeId(homeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_HOME));
        Dog dog = home.getDog();
        User invitedUser = UserCreateRequest.fromDto(request, encoder.encode(request.password()), home, dog);
        userRepository.save(invitedUser);
        return UserCreateResponse.of(UserDetailResponse.of(invitedUser), HomeDetailResponse.of(home),
                DogDetailResponse.of(dog), null);
    }

    @Transactional
    public UserDetail loadByUserId(Long userId) {
        User registedUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return UserDetail.of(registedUser);
    }
}