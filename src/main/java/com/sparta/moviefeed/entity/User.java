package com.sparta.moviefeed.entity;

import com.sparta.moviefeed.dto.requestdto.MypageRequestDto;
import com.sparta.moviefeed.dto.requestdto.PasswordRequestDto;
import com.sparta.moviefeed.dto.requestdto.UserSignupRequestDto;
import com.sparta.moviefeed.enumeration.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String intro;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    private String refreshToken;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime statusAt;

    public User(UserSignupRequestDto requestDto, UserStatus userStatus) {
        this.userId = requestDto.getUserId();
        this.password = requestDto.getPassword();
        this.userName = requestDto.getUserName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
        this.userStatus = userStatus;
    }

    public void updateMypage(MypageRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
    }

    public void updatePassword(PasswordRequestDto requestDto) {
        this.password = requestDto.getNewPassword();
    }

    public void encryptionPassword(String password) {
        this.password = password;
    }
  
}
