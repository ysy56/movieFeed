package com.sparta.moviefeed.enumeration;

import lombok.Getter;

@Getter
public enum UserStatus {

    NORMAL("NORMAL"),
    LEAVE("LEAVE"),
    EMAIL_AUTH("EMAIL_AUTH");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

}
