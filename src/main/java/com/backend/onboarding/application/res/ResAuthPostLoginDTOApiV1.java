package com.backend.onboarding.application.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResAuthPostLoginDTOApiV1 {

    private String token;

    public static ResAuthPostLoginDTOApiV1 of(String token) {
        return ResAuthPostLoginDTOApiV1.builder()
                .token(token)
                .build();
    }
}
