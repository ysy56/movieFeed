package com.sparta.moviefeed.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 10, max = 20, message = "아이디는 10글자 이상, 20글자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문 대소문자, 숫자만 가능합니다.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 10, message = "비밀번호는 10글자 이상 이여야 가능합니다.")
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=?<>{};:'/])[a-zA-Z\\d!@#$%^&*()_+=?<>{};:'/]+$", message = "비밀번호는 영문 대소문자 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식을 확인해 주세요.")
    private String email;

    @NotBlank(message = "한 줄 소개를 입력해주세요.")
    private String intro;

}
