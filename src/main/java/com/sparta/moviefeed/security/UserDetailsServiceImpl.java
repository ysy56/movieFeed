package com.sparta.moviefeed.security;

import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.exception.BadRequestException;
import com.sparta.moviefeed.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(userId).orElseThrow( () -> new BadRequestException("아이디, 비밀번호를 확인해주세요.1"));

        return new UserDetailsImpl(user);
    }

}
