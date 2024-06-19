package com.sparta.moviefeed.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 10, message = "비밀번호는 10글자 이상 이여야 가능합니다.")
    @Pattern(regexp = "(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+=?<>{};:'/])[a-zA-Z\\d!@#$%^&*()_+=?<>{};:'/]+$", message = "비밀번호는 영문 대소문자 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.")
    private String newPassword;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public PasswordRequestDto(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }
}
