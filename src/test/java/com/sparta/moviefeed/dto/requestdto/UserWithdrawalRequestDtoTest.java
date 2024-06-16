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
    private UserWithdrawalRequestDto userWithdrawalRequestDto;

    private String password;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        password = "password1234!";

        userWithdrawalRequestDto = new UserWithdrawalRequestDto(
                password
        );
    }

    @Test
    @DisplayName("비밀번호 수정 유효성 검사 실패 테스트")
    void testEmailCheckRequestDtoTestValidationFilure() {
        UserWithdrawalRequestDto invalidDto = new UserWithdrawalRequestDto(
                ""
        );

        Set<ConstraintViolation<UserWithdrawalRequestDto>> violations = validator.validate(invalidDto);

        for (ConstraintViolation<UserWithdrawalRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("비밀번호 수정 유효성 검사 성공 테스트")
    void testEmailCheckRequestDtoTestValidationSuccess() {
        Set<ConstraintViolation<UserWithdrawalRequestDto>> violations = validator.validate(userWithdrawalRequestDto);

        assertTrue(violations.isEmpty());
    }
}