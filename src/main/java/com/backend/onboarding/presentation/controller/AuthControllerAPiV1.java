package com.backend.onboarding.presentation.controller;

import com.backend.onboarding.application.res.ResAuthPostSignupDTOApiV1;
import com.backend.onboarding.domain.model.UserEntity;
import com.backend.onboarding.domain.model.constraint.RoleType;
import com.backend.onboarding.infrastructure.docs.AuthControllerApiV1Swagger;
import com.backend.onboarding.presentation.req.ReqAuthPostSignupDTOApiV1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthControllerAPiV1 implements AuthControllerApiV1Swagger {

    @PostMapping("/signup")
    public ResAuthPostSignupDTOApiV1 signup(@RequestBody @Valid ReqAuthPostSignupDTOApiV1 dto) {

        UserEntity dummy = UserEntity.create(
                "dummy-user",
                "dummy-password",
                "dummy-nickname",
                RoleType.USER
        );

        return ResAuthPostSignupDTOApiV1.of(dummy);
    }
}
