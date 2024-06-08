package com.sparta.moviefeed.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserWithdrawalRequestDto {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
