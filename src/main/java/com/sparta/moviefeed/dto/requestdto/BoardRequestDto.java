package com.sparta.moviefeed.dto.requestdto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;

    public BoardRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
