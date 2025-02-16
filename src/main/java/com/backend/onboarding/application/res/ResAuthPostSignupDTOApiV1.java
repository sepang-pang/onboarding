package com.backend.onboarding.application.res;

import com.backend.onboarding.domain.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResAuthPostSignupDTOApiV1 {

    private String username;
    private String nickname;
    private List<Authority> authorities;

    public static ResAuthPostSignupDTOApiV1 of(UserEntity userEntity) {
        return ResAuthPostSignupDTOApiV1.builder()
                .username(userEntity.getUsername())
                .nickname(userEntity.getNickname())
                .authorities(List.of(Authority
                        .from(userEntity.getRole().getAuthority()))
                )
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Authority {

        private String authorityName;

        public static Authority from(String authorityName) {
            return Authority.builder()
                    .authorityName(authorityName)
                    .build();
        }
    }
}
