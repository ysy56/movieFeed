package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.UserStatus;
import com.sparta.moviefeed.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void signup(UserSignupRequestDto requestDto) {

        UserStatus userStatus = UserStatus.NORMAL;
        User user = new User(requestDto, userStatus);

        userRepository.save(user);

    }

}
