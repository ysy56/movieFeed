package com.sparta.moviefeed.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(@JsonProperty("content")  String content) {
        this.content = content;
    }
}
