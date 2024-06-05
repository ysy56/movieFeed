package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.UserLoginRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.UserStatus;
import com.sparta.moviefeed.exception.BadRequestException;
import com.sparta.moviefeed.exception.ConflictException;
import com.sparta.moviefeed.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(UserSignupRequestDto requestDto) {

        if (findByUserId(requestDto.getUserId()).isPresent()) {
            throw new ConflictException("이미 사용 중인 아이디입니다.");
        }

        UserStatus userStatus = UserStatus.NORMAL;
        User user = new User(requestDto, userStatus);
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        user.encryptionPassword(encodedPassword);
        userRepository.save(user);

    }

    public void login(UserLoginRequestDto requestDto) {

        Optional<User> user = findByUserId(requestDto.getUserId());

        if (user.isEmpty() || user.get().getUserStatus().equals(UserStatus.LEAVE) || !checkPassword(requestDto.getPassword(), user.get().getPassword())) {
            throw new BadRequestException("아이디, 비밀번호를 확인해주세요.");
        }

    }

    public Optional<User> findByUserId(String userId) {

        Optional<User> user = userRepository.findByUserId(userId);

        return user;
    }

    public boolean checkPassword(String requestPassword, String databasePassword) {
        return passwordEncoder.matches(requestPassword, databasePassword);
    }

}
