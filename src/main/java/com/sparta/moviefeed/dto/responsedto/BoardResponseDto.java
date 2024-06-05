package com.sparta.moviefeed.dto.responsedto;

import com.sparta.moviefeed.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {
    private String title;
    private String content;
    private String username;
    private LocalDateTime updatedAt;

    public BoardResponseDto(Board board, String username) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = username;
        this.updatedAt = board.getUpdatedAt();
    }

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.username = board.getUser().getUserName();
        this.updatedAt = board.getUpdatedAt();
    }
}
