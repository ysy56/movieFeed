package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.enumeration.UserStatus;
import com.sparta.moviefeed.exception.ConflictException;
import com.sparta.moviefeed.exception.DataNotFoundException;
import com.sparta.moviefeed.repository.UserRepository;
import com.sparta.moviefeed.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
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

        findByUserId(requestDto.getUserId()).ifPresent( (el) -> {
            throw new ConflictException("이미 사용 중인 아이디입니다.");
        });

        UserStatus userStatus = UserStatus.NORMAL;
        User user = new User(requestDto, userStatus);
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        user.encryptionPassword(encodedPassword);
        userRepository.save(user);

    }

    @Transactional
    public void logout() {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = findByUserId(userDetails.getUsername()).orElseThrow( () -> new DataNotFoundException("해당 회원은 존재하지 않습니다."));

        user.updateRefreshToken(null);

    }

    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

}
