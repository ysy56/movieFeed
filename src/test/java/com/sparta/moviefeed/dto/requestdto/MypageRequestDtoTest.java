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

class MypageRequestDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 EmailCheckRequestDto 테스트")
    void testValidMypageRequestDto() {
        MypageRequestDto invalidDto = new MypageRequestDto("Test Username", "Test Introduction.");

        Set<ConstraintViolation<MypageRequestDto>> violations = validator.validate(invalidDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("빈 값 유효성 검사 실패 테스트")
    void testBlankFields() {
        MypageRequestDto invalidDto = new MypageRequestDto("", "");

        Set<ConstraintViolation<MypageRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size()); // email, authCode

        for (ConstraintViolation<MypageRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}