package com.sparta.moviefeed.enumeration;

import lombok.Getter;

@Getter
public enum UserStatus {

    NORMAL("NORMAL"),
    LEAVE("LEAVE");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

}
