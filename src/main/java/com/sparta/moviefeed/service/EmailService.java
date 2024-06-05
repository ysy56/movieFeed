package com.sparta.moviefeed.service;

import com.sparta.moviefeed.dto.requestdto.EmailCheckRequestDto;
import com.sparta.moviefeed.dto.requestdto.EmailRequestDto;
import com.sparta.moviefeed.exception.BadRequestException;
import com.sparta.moviefeed.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailService {

    @Value("${email.fromEmail}")
    private String fromEmail;

    private JavaMailSender mailSender;
    private RedisUtil redisUtil;
    private int authCode;

    public EmailService(JavaMailSender mailSender, RedisUtil redisUtil) {
        this.mailSender = mailSender;
        this.redisUtil = redisUtil;
    }

    public void makeRandomNumber() {
        Random rand = new Random();
        String randomNumber = "";
        for (int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(rand.nextInt(10));
        }

        authCode = Integer.parseInt(randomNumber);
    }

    public void joinEmail(EmailRequestDto requestDto) {
        makeRandomNumber();
        String toEmail = requestDto.getEmail();
        String title = "movieFeed 이메일 인증 코드입니다";
        String content =
                "movieFeed의 이메일 인증 테스트입니다." +
                "<br>" +
                "이메일 인증 요청을 받았음을 알려드립니다." +
                "<br><br>" +
                "인증번호는 " + authCode + " 입니다." +
                "<br><br>" +
                "인증번호 6자리를 제대로 입력해주세요.";
        sendEmail(toEmail, title, content);
    }

    public void sendEmail(String toEmail, String title, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        redisUtil.setDataExpire(Integer.toString(authCode),toEmail,60*3L);

    }

    public void checkAuthCode(EmailCheckRequestDto requestDto) {
        if (redisUtil.getData(requestDto.getAuthCode()) == null) {
            throw new DataNotFoundException("발급된 인증번호가 없습니다. 다시 이메일 인증을 시도해주세요.");
        }

        if (!redisUtil.getData(requestDto.getAuthCode()).equals(requestDto.getEmail())) {
            throw new BadRequestException("인증번호가 일치하지 않습니다.");
        }
    }
}
