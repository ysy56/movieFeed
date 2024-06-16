package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.dto.requestdto.CommentRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {
    private CommentRequestDto commentRequestDto;
    private Comment comment;

    private String content;
    private User user;
    private Board board;

    @BeforeEach
    void setUp() {
        content = "Test Content";
        user = new User();
        board = new Board();

        commentRequestDto = new CommentRequestDto(
                content
        );

        comment = new Comment(commentRequestDto, board, user);
    }

    @Test
    @DisplayName("객체 생성 확인")
    void testUserCreation() {
        assertEquals(content, comment.getContent());
        assertEquals(board, comment.getBoard());
        assertEquals(user, comment.getUser());
    }

    @Test
    @DisplayName("댓글 수정")
    void testUpdate() {
        String newContent = "Test NewContent";

        CommentRequestDto requestDto = new CommentRequestDto(newContent);

        comment.update(requestDto);

        assertEquals(newContent, comment.getContent());
    }
}