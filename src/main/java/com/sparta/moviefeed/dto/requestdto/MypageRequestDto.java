package com.sparta.moviefeed.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MypageRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "한 줄 소개를 입력해주세요.")
    private String intro;
}
