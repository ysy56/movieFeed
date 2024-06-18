package com.sparta.moviefeed.dto.requestdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sparta.moviefeed.dto.responsedto.CommentResponseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private String content;

    public CommentRequestDto() {
    }

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
