package com.developaw.harupuppy.domain.user.domain;

import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetail implements UserDetails {
    private Long userId;
    private String email;
    private String nickname;
    private UserRole userRole;
    private boolean isDeleted;
    private boolean allowNotification;
    private String homeId;
    private Long dogId;


    @Builder
    public UserDetail(Long userId, String email, String nickname, UserRole userRole, boolean isDeleted,
                      boolean allowNotification, String homeId, Long dogId) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
        this.isDeleted = isDeleted;
        this.allowNotification = allowNotification;
        this.homeId = homeId;
        this.dogId = dogId;
    }

    public static UserDetail of(User user){
        return UserDetail.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .userRole(user.getUserRole())
                .isDeleted(user.isDeleted())
                .allowNotification(user.isAllowNotification())
                .homeId(user.getHome().getHomeId())
                .dogId(user.getDog().getDogId())
                .build();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getUserRole().toString()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isDeleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isDeleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isDeleted;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted;
    }
}
