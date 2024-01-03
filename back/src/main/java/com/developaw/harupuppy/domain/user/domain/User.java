package com.developaw.harupuppy.domain.user.domain;

import com.developaw.harupuppy.domain.dog.domain.Dog;
import com.developaw.harupuppy.domain.home.domain.Home;
import com.developaw.harupuppy.domain.user.dto.UserUpdateRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Table(name = "`USERS`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NonNull
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "user_img")
    private String userImg;

    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "is_deleted", columnDefinition = "TINYINT(1)")
    private boolean isDeleted;

    @Column(name = "allow_notification", columnDefinition = "TINYINT(1)")
    private boolean allowNotification;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "home_id")
    private Home home;

    @Builder
    public User(String email, String userImg, String nickname, UserRole userRole, Dog dog, Home home) {
        this.email = email;
        this.userImg = userImg;
        this.nickname = nickname;
        this.userRole = userRole;
        this.dog = dog;
        this.home = home;
        this.isDeleted = false;
        this.allowNotification = true;
    }

    public void update (UserUpdateRequest updateRequest){
//        this.userImg = updateRequest.userImg();
        this.nickname = updateRequest.nickname();
        this.userRole = updateRequest.userRole();
    }

    public void setHome(Home home){
        this.home = home;
    }

    public void setDog(Dog dog){
        this.dog = dog;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
