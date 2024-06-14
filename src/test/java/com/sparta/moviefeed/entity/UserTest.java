package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.dto.requestdto.MypageRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.enumeration.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private UserSignupRequestDto userSignupRequestDto;
    private User user;

    private String userId;
    private String password;
    private String userName;
    private String email;
    private String intro;
    private UserStatus userStatus;

    @BeforeEach
    void setUp() {
        userId = "testUser123";
        password = "Password1234!";
        userName = "Test User";
        email = "testuser@example.com";
        intro = "Hello, I am a test user.";
        userStatus = UserStatus.NORMAL;

        userSignupRequestDto = new UserSignupRequestDto(
                userId,
                password,
                userName,
                email,
                intro
        );

        user = new User(userSignupRequestDto, userStatus);
    }

    @Test
    @DisplayName("객체 생성 확인")
    void testUserCreation() {
        assertEquals(userId, user.getUserId());
        assertEquals(password, user.getPassword());
        assertEquals(userName, user.getUserName());
        assertEquals(email, user.getEmail());
        assertEquals(intro, user.getIntro());
        assertEquals(userStatus, user.getUserStatus());
    }

    @Test
    @DisplayName("마이페이지 업데이트")
    void testUpdateMypage() {
        String newUserName = "Update User";
        String newIntro = "Update intro.";

        MypageRequestDto RequestDto = new MypageRequestDto(newUserName, newIntro);

        user.updateMypage(RequestDto);

        assertEquals(newUserName, user.getUserName());
        assertEquals(newIntro, user.getIntro());
    }
}