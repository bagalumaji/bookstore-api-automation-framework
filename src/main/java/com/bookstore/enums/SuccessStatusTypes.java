package com.bookstore.enums;

import lombok.Getter;

@Getter
public enum SuccessStatusTypes {
    OK(200, "HTTP/1.1 200 OK"),
    CREATED(201, "CREATED"),
    ACCEPTED(202, "ACCEPTED"),
    NO_CONTENT(204, "NO_CONTENT");

    private final int code;
    private final String message;

    SuccessStatusTypes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

