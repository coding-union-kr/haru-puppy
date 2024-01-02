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
    private String nickName;
    private UserRole userRole;
    private boolean isDeleted;
    private boolean allowNotification;

    @Builder
    public UserDetail(Long userId, String email, String nickName, UserRole userRole, boolean isDeleted,
                      boolean allowNotification) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.userRole = userRole;
        this.isDeleted = isDeleted;
        this.allowNotification = allowNotification;
    }

    public static UserDetail of(User user){
        return UserDetail.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickName(user.getNickname())
                .userRole(user.getUserRole())
                .isDeleted(user.isDeleted())
                .allowNotification(user.isAllowNotification())
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
