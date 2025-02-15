package com.backend.onboarding.application.res;

import com.backend.onboarding.domain.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResAuthPostSignupDTOApiV1 {

    private String username;
    private String nickname;

    // --
    // TODO : 권한 추가 필요
    // --

    public static ResAuthPostSignupDTOApiV1 of(UserEntity userEntity) {
        return ResAuthPostSignupDTOApiV1.builder()
                .username(userEntity.getUsername())
                .nickname(userEntity.getNickname())
                .build();
    }
}
