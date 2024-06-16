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
    private MypageRequestDto mypageRequestDto;

    private String userName;
    private String intro;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        userName = "Test Name";
        intro = "Test intro.";

        mypageRequestDto = new MypageRequestDto(
                userName,
                intro
        );
    }

    @Test
    @DisplayName("마이페이지 유효성 검사 실패 테스트")
    void testEmailCheckRequestDtoTestValidationFilure() {
        MypageRequestDto invalidDto = new MypageRequestDto(
                "", // Invalid email
                "" // Invalid authCode
        );

        Set<ConstraintViolation<MypageRequestDto>> violations = validator.validate(invalidDto);

        for (ConstraintViolation<MypageRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("이메일 인증 유효성 검사 성공 테스트")
    void testEmailCheckRequestDtoTestValidationSuccess() {
        Set<ConstraintViolation<MypageRequestDto>> violations = validator.validate(mypageRequestDto);

        assertTrue(violations.isEmpty());
    }
}