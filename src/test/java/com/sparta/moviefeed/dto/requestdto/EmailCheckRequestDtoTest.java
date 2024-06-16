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

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 EmailCheckRequestDto 테스트")
    void testValidEmailCheckRequestDto() {
        EmailCheckRequestDto invalidDto = new EmailCheckRequestDto("test@naver.com", "123456");

        Set<ConstraintViolation<EmailCheckRequestDto>> violations = validator.validate(invalidDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("유효하지 않은 EmailCheckRequestDto 테스트")
    void testInValidEmailCheckRequestDto() {
        EmailCheckRequestDto invalidDto = new EmailCheckRequestDto("invalid-email", "");

        Set<ConstraintViolation<EmailCheckRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());

        for (ConstraintViolation<EmailCheckRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("Email 유효성 검사 실패 테스트")
    void testInvalidEmail() {
        EmailCheckRequestDto invalidDto = new EmailCheckRequestDto("invalid-email", "123456");

        Set<ConstraintViolation<EmailCheckRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        assertEquals("이메일 형식을 확인해 주세요.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("빈 값 유효성 검사 실패 테스트")
    void testBlankFields() {
        EmailCheckRequestDto invalidDto = new EmailCheckRequestDto("", "");

        Set<ConstraintViolation<EmailCheckRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size()); // email, authCode

        for (ConstraintViolation<EmailCheckRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}