package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.dto.requestdto.MypageRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.enumeration.UserStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Validator validator;
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
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

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

    @Test
    @DisplayName("비밀번호 수정")
    void testUpdatePassword() {
        String newPassword = "qwer1234!";

        user.updatePassword(newPassword);

        assertEquals(newPassword, user.getPassword());
    }

    @Test
    @DisplayName("리프레시 토큰 업데이트")
    void testUpdateRefreshToken() {
        String newRefreshToken = "newRefreshToken";

        user.updateRefreshToken(newRefreshToken);

        assertEquals(newRefreshToken, user.getRefreshToken());
    }

    @Test
    @DisplayName("회원상태코드 업데이트")
    void testUpdateUserStatus() {
        UserStatus newUserStatus = UserStatus.LEAVE;
        LocalDateTime now = LocalDateTime.now();

        user.updateUserStatus(newUserStatus, now);

        assertEquals(newUserStatus, user.getUserStatus());
        assertEquals(now, user.getStatusAt());
    }

    @Test
    @DisplayName("비밀번호 암호화")
    void testEncryptionPassword() {
        String newEncryptionPassword = "EncryptedPassword123!";

        user.updatePassword(newEncryptionPassword);

        assertEquals(newEncryptionPassword, user.getPassword());
    }

    @Test
    @DisplayName("회원가입 유효성 검사 실패 테스트")
    void testUserSignupRequestDtoValidationFilure() {
        UserSignupRequestDto invalidDto = new UserSignupRequestDto(
                "", // Invalid userId
                "short", // Invalid password
                "", // Invalid userName
                "invalid-email", // Invalid email
                "" // Invalid intro
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(invalidDto);

        for (ConstraintViolation<UserSignupRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}