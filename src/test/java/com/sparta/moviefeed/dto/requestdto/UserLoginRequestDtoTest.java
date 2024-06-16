package com.sparta.moviefeed.dto.requestdto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserLoginRequestDtoTest {
    private Validator validator;
    private UserLoginRequestDto userLoginRequestDto;

    private String userId;
    private String Password;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        userId = "Test UserId";
        Password = "Password1234!";

        userLoginRequestDto = new UserLoginRequestDto(
                userId,
                Password
        );
    }

    @Test
    @DisplayName("로그인 유효성 검사 실패 테스트")
    void testEmailCheckRequestDtoTestValidationFilure() {
        UserLoginRequestDto invalidDto = new UserLoginRequestDto(
                "", // Invalid email
                "" // Invalid authCode
        );

        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(invalidDto);

        for (ConstraintViolation<UserLoginRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("비밀번호 수정 유효성 검사 성공 테스트")
    void testEmailCheckRequestDtoTestValidationSuccess() {
        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(userLoginRequestDto);

        assertTrue(violations.isEmpty());
    }
}