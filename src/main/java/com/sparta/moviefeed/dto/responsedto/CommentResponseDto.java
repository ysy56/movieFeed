package com.sparta.moviefeed.dto.responsedto;

import com.sparta.moviefeed.dto.requestdto.CommentRequestDto;
import com.sparta.moviefeed.entity.Comment;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final String content;
    private final LocalDateTime updateAt;

    public CommentResponseDto(Comment comment) {
        this.content = comment.getContent();
        this.updateAt = comment.getUpdatedAt();
    }
}
