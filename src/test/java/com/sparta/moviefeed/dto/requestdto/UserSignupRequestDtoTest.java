package com.sparta.moviefeed.dto.requestdto;

import com.sparta.moviefeed.enumeration.UserStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserSignupRequestDtoTest {

    private Validator validator;
    private UserSignupRequestDto userSignupRequestDto;

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

    @Test
    @DisplayName("비밀번호 수정 유효성 검사 성공 테스트")
    void testEmailCheckRequestDtoTestValidationSuccess() {
        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(userSignupRequestDto);

        assertTrue(violations.isEmpty());
    }
}