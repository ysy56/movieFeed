package com.sparta.moviefeed.dto.responsedto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final int httpStatusCode;
    private final String message;

    public UserResponseDto(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

}
