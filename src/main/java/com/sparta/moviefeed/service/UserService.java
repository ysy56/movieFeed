package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.UserStatus;
import com.sparta.moviefeed.exception.ConflictException;
import com.sparta.moviefeed.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void signup(UserSignupRequestDto requestDto) {

        if (findByUserId(requestDto.getUserId()).isPresent()) {
            throw new ConflictException("이미 사용 중인 아이디입니다.");
        }

        UserStatus userStatus = UserStatus.NORMAL;
        User user = new User(requestDto, userStatus);

        userRepository.save(user);

    }

    public Optional<User> findByUserId(String userId) {

        Optional<User> user = userRepository.findByUserId(userId);

        return user;
    }

}