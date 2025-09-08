package com.bookstore.enums;

import lombok.Getter;

@Getter
public enum StatusTypes {
    OK(200, "HTTP/1.1 200 OK"),
    BADREQUEST(400, "HTTP/1.1 400 Bad Request"),
    ACCEPTED(202, "ACCEPTED"),
    NO_CONTENT(204, "NO_CONTENT");

    private final int code;
    private final String message;

    StatusTypes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

