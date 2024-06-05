package com.sparta.moviefeed.dto.responsedto;

import com.sparta.moviefeed.entity.User;
import lombok.Getter;

@Getter
public class MypageResponseDto {
    private String userId;
    private String userName;
    private String intro;
    private String email;

    public MypageResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.intro = user.getIntro();
        this.email = user.getEmail();
    }

}
