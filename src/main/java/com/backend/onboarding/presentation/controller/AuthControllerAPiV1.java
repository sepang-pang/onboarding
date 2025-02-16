package com.backend.onboarding.presentation.controller;

import com.backend.onboarding.application.res.ResAuthPostLoginDTOApiV1;
import com.backend.onboarding.application.res.ResAuthPostSignupDTOApiV1;
import com.backend.onboarding.application.service.AuthServiceApiV1;
import com.backend.onboarding.infrastructure.docs.AuthControllerApiV1Swagger;
import com.backend.onboarding.presentation.req.ReqAuthPostLoginDTOApiV1;
import com.backend.onboarding.presentation.req.ReqAuthPostSignupDTOApiV1;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerAPiV1 implements AuthControllerApiV1Swagger {

    private final AuthServiceApiV1 authServiceApiV1;

    @PostMapping("/signup")
    public ResponseEntity<ResAuthPostSignupDTOApiV1> signup(@RequestBody @Valid ReqAuthPostSignupDTOApiV1 dto) {
        return new ResponseEntity<>(
                authServiceApiV1.signup(dto),
                HttpStatus.OK
        );
    }

    @PostMapping("/sign")
    public ResponseEntity<ResAuthPostLoginDTOApiV1> sign(@RequestBody @Valid ReqAuthPostLoginDTOApiV1 dto, HttpServletResponse response) {
        return new ResponseEntity<>(
                authServiceApiV1.sign(dto, response),
                HttpStatus.OK
        );
    }

}
