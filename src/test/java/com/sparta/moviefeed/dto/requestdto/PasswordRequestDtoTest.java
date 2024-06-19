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

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 PasswordRequestDto 테스트")
    void testValidPasswordRequestDto() {
        PasswordRequestDto invalidDto = new PasswordRequestDto("password1234!", "newPassword1234!");

        Set<ConstraintViolation<PasswordRequestDto>> violations = validator.validate(invalidDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("유효하지 않은 PasswordRequestDto 테스트")
    void testInValidPasswordRequestDto() {
        PasswordRequestDto invalidDto = new PasswordRequestDto("", "invalid-passowrd");

        Set<ConstraintViolation<PasswordRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());

        for (ConstraintViolation<PasswordRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("newPassword 길이 유효성 검사 실패 테스트")
    void testShortPassword() {
        PasswordRequestDto invalidDto = new PasswordRequestDto("password1234!", "qwer1234!");

        Set<ConstraintViolation<PasswordRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("비밀번호는 10글자 이상 이여야 가능합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("newPassword 정규식 유효성 검사 실패 테스트")
    void testRegexpPassword() {
        PasswordRequestDto invalidDto = new PasswordRequestDto("password1234!", "invalid-password");

        Set<ConstraintViolation<PasswordRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        assertEquals("비밀번호는 영문 대소문자 + 숫자 + 특수문자를 최소 1글자씩 포함합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("빈 값 유효성 검사 실패 테스트")
    void testBlankFields() {
        PasswordRequestDto invalidDto = new PasswordRequestDto("", "");

        Set<ConstraintViolation<PasswordRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(4, violations.size());

        for (ConstraintViolation<PasswordRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}