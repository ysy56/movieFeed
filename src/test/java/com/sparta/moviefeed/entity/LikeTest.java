package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.enumeration.LikeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeTest {
    private Like like;

    private User user;
    private Board board;
    private LikeType likeType;

    @BeforeEach
    void setUp() {
        likeType = LikeType.BOARD;

        like = new Like(user, board, likeType);
    }

    @Test
    @DisplayName("객체 생성 확인")
    void testUserCreation() {
        assertEquals(user, like.getUser());
        assertEquals(board, like.getBoard());
        assertEquals(likeType, like.getLikeType());
    }
}