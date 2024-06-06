package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.UserLoginRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.UserStatus;
import com.sparta.moviefeed.exception.BadRequestException;
import com.sparta.moviefeed.exception.ConflictException;
import com.sparta.moviefeed.repository.UserRepository;
import com.sparta.moviefeed.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public void signup(UserSignupRequestDto requestDto) {

        findByUserId(requestDto.getUserId()).ifPresent( (el) -> {
            throw new ConflictException("이미 사용 중인 아이디입니다.");
        });

        UserStatus userStatus = UserStatus.NORMAL;
        User user = new User(requestDto, userStatus);
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        user.encryptionPassword(encodedPassword);
        userRepository.save(user);

    }

    public void login(UserLoginRequestDto requestDto, HttpServletResponse response) {

        Optional<User> user = findByUserId(requestDto.getUserId());

        if (user.isEmpty() || user.get().getUserStatus().equals(UserStatus.LEAVE) || !checkPassword(requestDto.getPassword(), user.get().getPassword())) {
            throw new BadRequestException("아이디, 비밀번호를 확인해주세요.");
        }

        String accessToken = jwtUtil.generateAccessToken(user.get().getUserId(), user.get().getUserName());
        String refreshToken = jwtUtil.generateRefreshToken(user.get().getUserId(), user.get().getUserName());
        ResponseCookie responseCookie = jwtUtil.generateRefreshTokenCookie(refreshToken);

        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        user.get().updateRefreshToken(refreshToken);
        userRepository.save(user.get());

    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean checkPassword(String requestPassword, String databasePassword) {
        return passwordEncoder.matches(requestPassword, databasePassword);
    }

}
