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

class PasswordRequestDtoTest {
    private Validator validator;
    private PasswordRequestDto passwordRequestDto;

    private String password;
    private String newPassword;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        password = "password1234!";
        newPassword = "newPassword1234!";

        passwordRequestDto = new PasswordRequestDto(
                password,
                newPassword
        );
    }

    @Test
    @DisplayName("비밀번호 수정 유효성 검사 실패 테스트")
    void testEmailCheckRequestDtoTestValidationFilure() {
        PasswordRequestDto invalidDto = new PasswordRequestDto(
                "", // Invalid email
                "" // Invalid authCode
        );

        Set<ConstraintViolation<PasswordRequestDto>> violations = validator.validate(invalidDto);

        for (ConstraintViolation<PasswordRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("비밀번호 수정 유효성 검사 성공 테스트")
    void testEmailCheckRequestDtoTestValidationSuccess() {
        Set<ConstraintViolation<PasswordRequestDto>> violations = validator.validate(passwordRequestDto);

        assertTrue(violations.isEmpty());
    }
}