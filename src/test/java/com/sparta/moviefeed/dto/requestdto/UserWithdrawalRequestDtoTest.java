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

class UserWithdrawalRequestDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 UserWithdrawalRequestDto 테스트")
    void testValidUserWithdrawalRequestDto() {
        UserWithdrawalRequestDto invalidDto = new UserWithdrawalRequestDto("Test Password");

        Set<ConstraintViolation<UserWithdrawalRequestDto>> violations = validator.validate(invalidDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("빈 값 유효성 검사 실패 테스트")
    void testBlankFields() {
        UserWithdrawalRequestDto invalidDto = new UserWithdrawalRequestDto("");

        Set<ConstraintViolation<UserWithdrawalRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size()); // email, authCode
        assertEquals("비밀번호를 입력해주세요.", violations.iterator().next().getMessage());
    }
}