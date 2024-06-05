package com.sparta.moviefeed.dto.responsedto;

import lombok.Getter;

@Getter
public class CommonResponse<T> {
    private Integer httpStatusCode;
    private String message;
    private T data;

    public CommonResponse(int httpStatusCode, String message, T data) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.data = data;
    }

    public CommonResponse(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }
}
