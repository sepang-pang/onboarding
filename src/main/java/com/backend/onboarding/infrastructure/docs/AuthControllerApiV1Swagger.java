package com.backend.onboarding.infrastructure.docs;

import com.backend.onboarding.application.res.ResAuthPostLoginDTOApiV1;
import com.backend.onboarding.application.res.ResAuthPostSignupDTOApiV1;
import com.backend.onboarding.presentation.req.ReqAuthPostLoginDTOApiV1;
import com.backend.onboarding.presentation.req.ReqAuthPostSignupDTOApiV1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "회원가입, 로그인 인증 등 인증 관련 API를 제공합니다.")
public interface AuthControllerApiV1Swagger {

    @Operation(summary = "회원가입", description = "회원가입을 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResAuthPostSignupDTOApiV1.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패.", content = @Content(schema = @Schema(implementation = ResAuthPostSignupDTOApiV1.class)))
    })
    @PostMapping("/signup")
    ResponseEntity<ResAuthPostSignupDTOApiV1> signup(@RequestBody @Valid ReqAuthPostSignupDTOApiV1 dto);


    @Operation(summary = "로그인", description = "로그인을 하는 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResAuthPostSignupDTOApiV1.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패.", content = @Content(schema = @Schema(implementation = ResAuthPostSignupDTOApiV1.class)))
    })
    @PostMapping("/sign")
    ResponseEntity<ResAuthPostLoginDTOApiV1> sign(@RequestBody @Valid ReqAuthPostLoginDTOApiV1 dto, HttpServletResponse response);

}
