package com.developaw.harupuppy.fixture;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.dog.domain.DogGender;
import com.developaw.harupuppy.domain.user.domain.Home;
import com.developaw.harupuppy.domain.user.domain.UserRole;
import com.developaw.harupuppy.domain.user.dto.request.DogCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.HomeCreateRequest;
import com.developaw.harupuppy.domain.user.dto.request.UserCreateRequest;
import java.time.LocalDate;

public class UserFixture {
    public static DogCreateRequest getDogRequest(){
        return new DogCreateRequest("강아지", 3.4, DogGender.FEMALE,
                "2023-10-23", "imgUrl");
    }
    public static UserCreateRequest getUserRequest(){
        return new UserCreateRequest("email@test.com", "유저테스트", "imgUrl", UserRole.UNNIE);
    }
    public static HomeCreateRequest getHomeRequest(){
        return new HomeCreateRequest(UserFixture.getUserRequest(), UserFixture.getDogRequest(), "homeName");
    }
}
