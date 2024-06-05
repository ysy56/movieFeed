package com.sparta.moviefeed.controller;

import com.sparta.moviefeed.dto.requestdto.MypageRequestDto;
import com.sparta.moviefeed.dto.requestdto.PasswordRequestDto;
import com.sparta.moviefeed.dto.responsedto.CommonResponse;
import com.sparta.moviefeed.dto.responsedto.MypageResponseDto;
import com.sparta.moviefeed.service.MypageService;
import jakarta.validation.Valid;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<MypageResponseDto>> getMypage() {
        MypageResponseDto responseDto = mypageService.getMypage();
        CommonResponse response = new CommonResponse(200, "마이페이지 조회 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MypageResponseDto>> updateMypage(@Valid @RequestBody MypageRequestDto requestDto) {
        MypageResponseDto responseDto = mypageService.updateMypage(requestDto);
        CommonResponse response = new CommonResponse(200, "마이페이지 수정 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<CommonResponse> updatePassword(@Valid @RequestBody PasswordRequestDto requestDto) {
        mypageService.updatePassword(requestDto);
        CommonResponse response = new CommonResponse(200, "비밀번호 수정 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
