package com.sparta.moviefeed.controller;

import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.dto.responsedto.UserResponseDto;
import com.sparta.moviefeed.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userservice;

    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSignupRequestDto requestDto) {
        userservice.signup(requestDto);

        UserResponseDto userResponseDto = new UserResponseDto(201, "회원가입 성공");

        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);
    }

}
