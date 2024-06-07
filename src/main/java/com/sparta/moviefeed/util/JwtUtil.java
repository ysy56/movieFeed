package com.sparta.moviefeed.util;

import com.sparta.moviefeed.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

@Getter
@Component
public class JwtUtil {

    @Value("${ACCESS_TOKEN_TIME}")
    private long ACCESS_TOKEN_TIME;

    @Value("${REFRESH_TOKEN_TIME}")
    private long REFRESH_TOKEN_TIME;

    public static final String BEARER_PREFIX = "Bearer ";
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final JwtConfig jwtConfig;
    private final Key key;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.key = jwtConfig.getKey();
    }

    public String generateToken(String userId, String userName, long tokenTime) {

        Date date = new Date();

        return Jwts.builder()
                        .setSubject(userId)
                        .claim("userName", userName)
                        .setExpiration(new Date(date.getTime() + tokenTime))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String generateAccessToken(String userId, String userName) {
        return generateToken(userId, userName, ACCESS_TOKEN_TIME);
    }

    public String generateRefreshToken(String userId, String userName) {
        return generateToken(userId, userName, REFRESH_TOKEN_TIME);
    }

    public ResponseCookie generateRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .maxAge(REFRESH_TOKEN_TIME / 1000)
                .path("/")
                .sameSite("Strict")
                .build();
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(7);
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
