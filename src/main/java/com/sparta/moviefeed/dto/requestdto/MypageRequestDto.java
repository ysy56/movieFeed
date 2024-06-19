package com.sparta.moviefeed.dto.requestdto;

import com.sparta.moviefeed.dto.responsedto.MypageResponseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MypageRequestDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "한 줄 소개를 입력해주세요.")
    private String intro;

    public MypageRequestDto(String userName, String intro) {
        this.userName = userName;
        this.intro = intro;
    }
}
