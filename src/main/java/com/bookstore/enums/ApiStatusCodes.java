package com.bookstore.enums;

import lombok.Getter;

@Getter
public enum ApiStatusCodes {
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    NOT_AUTHENTICATED(403);
    private final int code;

    ApiStatusCodes(int code) {
        this.code = code;
    }
}

