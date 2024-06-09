package com.sparta.moviefeed.controller;

import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserWithdrawalRequestDto;
import com.sparta.moviefeed.dto.responsedto.CommonResponse;
import com.sparta.moviefeed.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logout() {
        userservice.logout();

        CommonResponse<Void> commonResponse = new CommonResponse<>(200, "로그아웃 성공");

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PutMapping("/withdrawal")
    public ResponseEntity<CommonResponse<Void>> withdrawal(@Valid @RequestBody UserWithdrawalRequestDto requestDto) {
        userservice.withdrawal(requestDto);

        CommonResponse<Void> commonResponse = new CommonResponse<>(200, "회원탈퇴 성공");

        return new ResponseEntity<>(commonResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponse<Void>> refresh(HttpServletRequest request) {

        String accessToken = userservice.refresh(request);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        CommonResponse<Void> commonResponse = new CommonResponse<>(200, "RefreshToken 인증 성공");

        return new ResponseEntity<>(commonResponse, headers, HttpStatus.OK);
    }

}
