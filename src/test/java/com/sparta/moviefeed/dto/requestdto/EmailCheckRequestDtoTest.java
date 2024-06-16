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

class EmailCheckRequestDtoTest {
    private Validator validator;
    private EmailCheckRequestDto emailCheckRequestDto;

    private String authCode;
    private String email;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        email = "testuser@example.com";
        authCode = "123895";

        emailCheckRequestDto = new EmailCheckRequestDto(
                email,
                authCode
        );
    }

    @Test
    @DisplayName("이메일 인증 유효성 검사 실패 테스트")
    void testEmailCheckRequestDtoTestValidationFilure() {
        EmailCheckRequestDto invalidDto = new EmailCheckRequestDto(
                "", // Invalid email
                "" // Invalid authCode
        );

        Set<ConstraintViolation<EmailCheckRequestDto>> violations = validator.validate(invalidDto);

        for (ConstraintViolation<EmailCheckRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("이메일 인증 유효성 검사 성공 테스트")
    void testEmailCheckRequestDtoTestValidationSuccess() {
        Set<ConstraintViolation<EmailCheckRequestDto>> violations = validator.validate(emailCheckRequestDto);

        assertTrue(violations.isEmpty());
    }
}