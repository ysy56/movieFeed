package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.MypageRequestDto;
import com.sparta.moviefeed.dto.requestdto.PasswordRequestDto;
import com.sparta.moviefeed.dto.responsedto.MypageResponseDto;
import com.sparta.moviefeed.entity.User;
import com.sparta.moviefeed.exception.BadRequestException;
import com.sparta.moviefeed.exception.ConflictException;
import com.sparta.moviefeed.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MypageService {
    private static final Logger log = LoggerFactory.getLogger(MypageService.class);
    // security 사용X : 테스트를 위한 코드
    private final Long userId = 2L;

    private final UserRepository userRepository;

    public MypageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MypageResponseDto getMypage() {
//        // security 사용
//        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userDetails.getUser();  // 여기서 user를 가져옵니다.

        // security 사용X : 테스트를 위한 코드
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다.")
        );

        return new MypageResponseDto(user);
    }

    @Transactional
    public MypageResponseDto updateMypage(MypageRequestDto requestDto) {
//        // security 사용
//        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userDetails.getUser();  // 여기서 user를 가져옵니다.

        // security 사용X : 테스트를 위한 코드
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다.")
        );

        user.updateMypage(requestDto);

        return new MypageResponseDto(user);
    }

    @Transactional
    public void updatePassword(PasswordRequestDto requestDto) {
//        // security 사용
//        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        User user = userDetails.getUser();  // 여기서 user를 가져옵니다.

        // security 사용X : 테스트를 위한 코드
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자는 존재하지 않습니다.")
        );

        if (user.getPassword().equals(requestDto.getPassword())) {
            if (!requestDto.getPassword().equals(requestDto.getNewPassword())) {
                user.updatePassword(requestDto);
            } else {
                throw new ConflictException("현재 비밀번호와 새로운 비밀번호가 같습니다.");
            }
        } else {
            throw new BadRequestException("비밀번호가 틀렸습니다.");
        }

    }
}
