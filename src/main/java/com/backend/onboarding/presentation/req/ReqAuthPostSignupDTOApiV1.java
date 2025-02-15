package com.backend.onboarding.presentation.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReqAuthPostSignupDTOApiV1 {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-z][a-z0-9]{7,15}$", message = "아이디는 8~16자의 소문자 영문자와 숫자만 사용할 수 있으며, 첫 글자는 영문자여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임를 입력해주세요.")
    private String nickname;

}
