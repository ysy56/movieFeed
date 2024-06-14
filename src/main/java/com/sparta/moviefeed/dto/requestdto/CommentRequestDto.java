package com.sparta.moviefeed.dto.requestdto;

import com.sparta.moviefeed.dto.responsedto.CommentResponseDto;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
