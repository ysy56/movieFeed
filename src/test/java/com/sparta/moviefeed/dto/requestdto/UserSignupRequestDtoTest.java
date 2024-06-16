package com.sparta.moviefeed.dto.requestdto;

import com.sparta.moviefeed.enumeration.UserStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserSignupRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("유효한 UserSignupRequestDto 테스트")
    void testValidUserSignupRequestDto() {
        UserSignupRequestDto validDto = new UserSignupRequestDto(
                "validUserId123",
                "ValidPassword123!",
                "Valid User",
                "validuser@example.com",
                "Hello, I am a valid user."
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(validDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("유효하지 않은 UserSignupRequestDto 테스트")
    void testInvalidUserSignupRequestDto() {
        UserSignupRequestDto invalidDto = new UserSignupRequestDto(
                "short",
                "invalidPassword",
                "",
                "invalid-email",
                ""
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(5, violations.size()); // 모든 필드에 대한 유효성 검사 오류

        for (ConstraintViolation<UserSignupRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    @DisplayName("아이디 형식 유효성 검사 실패 테스트")
    void testInvalidUserIdPattern() {
        UserSignupRequestDto invalidDto = new UserSignupRequestDto(
                "invalid ID",
                "ValidPassword123!",
                "Valid User",
                "validuser@example.com",
                "Hello, I am a valid user."
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("아이디는 영문 대소문자, 숫자만 가능합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("아이디 길이 유효성 검사 실패 테스트")
    void testInvalidUserIdLength() {
        UserSignupRequestDto invalidDto = new UserSignupRequestDto(
                "short",
                "ValidPassword123!",
                "Valid User",
                "validuser@example.com",
                "Hello, I am a valid user."
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("아이디는 10글자 이상, 20글자 이하여야 합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("비밀번호 길이 유효성 검사 실패 테스트")
    void testInvalidPasswordLength() {
        UserSignupRequestDto invalidDto = new UserSignupRequestDto(
                "validUserId123",
                "qwer1234!",
                "Valid User",
                "validuser@example.com",
                "Hello, I am a valid user."
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("비밀번호는 10글자 이상 이여야 가능합니다.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("이메일 형식 유효성 검사 실패 테스트")
    void testInvalidEmailPattern() {
        UserSignupRequestDto invalidDto = new UserSignupRequestDto(
                "validUserId123",
                "ValidPassword123!",
                "Valid User",
                "invalid-email",
                "Hello, I am a valid user."
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("이메일 형식을 확인해 주세요.", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("빈 값 유효성 검사 실패 테스트")
    void testBlankFields() {
        UserSignupRequestDto invalidDto = new UserSignupRequestDto(
                "",
                "",
                "",
                "",
                ""
        );

        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(invalidDto);

        assertFalse(violations.isEmpty());
        assertEquals(10, violations.size()); // 모든 필드에 대한 유효성 검사 오류

        for (ConstraintViolation<UserSignupRequestDto> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }
}