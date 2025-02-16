package com.backend.onboarding.infrastructure.security;

import com.backend.onboarding.domain.model.UserEntity;
import com.backend.onboarding.domain.model.constraint.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    public static CustomUserDetails of(UserEntity userEntity) {
        return CustomUserDetails.builder()
                .user(User.from(userEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        private Long id;
        private String username;
        private String password;
        private RoleType role;

        public static User from(UserEntity userEntity) {
            return User.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .role(userEntity.getRole())
                    .build();
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        RoleType role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
