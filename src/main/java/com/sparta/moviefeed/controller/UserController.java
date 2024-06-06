package com.sparta.moviefeed.controller;

import com.sparta.moviefeed.dto.requestdto.UserLoginRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.dto.responsedto.CommonResponse;
import com.sparta.moviefeed.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
    public ResponseEntity<CommonResponse<Void>> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {
        userservice.signup(requestDto);

        CommonResponse<Void> commonResponse = new CommonResponse<>(201, "회원가입 성공");

        return new ResponseEntity<>(commonResponse, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> login(@Valid @RequestBody UserLoginRequestDto requestDto, HttpServletResponse response) {
        userservice.login(requestDto, response);

        CommonResponse<Void> commonResponse = new CommonResponse<>(200, "로그인 성공");

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

}
