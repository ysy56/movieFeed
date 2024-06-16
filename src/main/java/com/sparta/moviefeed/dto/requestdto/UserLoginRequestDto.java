package com.sparta.moviefeed.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public UserLoginRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

}
