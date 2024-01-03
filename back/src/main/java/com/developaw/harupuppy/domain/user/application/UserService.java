package com.developaw.harupuppy.domain.user.application;



import com.developaw.harupuppy.domain.user.domain.User;
import com.developaw.harupuppy.domain.user.dto.UserResponse;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import com.developaw.harupuppy.domain.user.repository.UserRepository;
import com.developaw.harupuppy.global.common.exception.CustomException;
import com.developaw.harupuppy.global.common.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse updateUserInformation (UserUpdateRequest request){
        User user = userRepository.findUserByUserId(request.userId())
                .orElseThrow(() -> new CustomException(Response.ErrorCode.NOT_FOUND_USER));
        user.update(request);
        return UserResponse.of(user);
    }

}
