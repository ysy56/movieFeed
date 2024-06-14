package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.dto.requestdto.BoardRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private BoardRequestDto boardRequestDto;
    private Board board;

    private String title;
    private String content;
    private User user;

    @BeforeEach
    void setUp() {
        title = "Test Title";
        content = "Test Content";

        boardRequestDto = new BoardRequestDto(
                title,
                content
        );

        board = new Board(boardRequestDto, user);
    }

    @Test
    @DisplayName("객체 생성 확인")
    void testUserCreation() {
        assertEquals(title, board.getTitle());
        assertEquals(content, board.getContent());
        assertEquals(user, board.getUser());
    }

    @Test
    @DisplayName("게시물 수정")
    void testUpdate() {
        String newTitle = "Test NewTitle";
        String newContent = "Test NewContent";

        BoardRequestDto requestDto = new BoardRequestDto(newTitle, newContent);

        board.update(requestDto);

        assertEquals(newTitle, board.getTitle());
        assertEquals(newContent, board.getContent());
    }
}