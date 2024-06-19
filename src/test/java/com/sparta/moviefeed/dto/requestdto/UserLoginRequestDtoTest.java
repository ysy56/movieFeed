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

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 UserLoginRequestDto 테스트")
    void testValidUserLoginRequestDto() {
        UserLoginRequestDto invalidDto = new UserLoginRequestDto("Test UserId", "Test Password");

        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(invalidDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("빈 값 유효성 검사 실패 테스트")
    void testBlankFields() {
        UserLoginRequestDto invalidDto = new UserLoginRequestDto("", "");

        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size()); // email, authCode

        for (ConstraintViolation<UserLoginRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}