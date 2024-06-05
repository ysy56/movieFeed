package com.sparta.moviefeed.dto.requestdto;

import lombok.Getter;

@Getter
public class UserSignupRequestDto {

    private String userId;
    private String password;
    private String userName;
    private String email;
    private String intro;

}
