package com.backend.onboarding.presentation.controller;

import com.backend.onboarding.application.res.ResAuthPostSignupDTOApiV1;
import com.backend.onboarding.application.service.AuthServiceApiV1;
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

    private final AuthServiceApiV1 authServiceApiV1;

    @PostMapping("/signup")
    public ResAuthPostSignupDTOApiV1 signup(@RequestBody @Valid ReqAuthPostSignupDTOApiV1 dto) {
        return authServiceApiV1.signup(dto);
    }

}
