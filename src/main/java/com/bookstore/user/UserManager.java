package com.bookstore.user;

import com.bookstore.pojo.UserCredentials;

public final class UserManager {
    private static final ThreadLocal<UserCredentials> USER_MANAGER = new ThreadLocal<>();

    public static UserCredentials getUserCredentials() {
        return USER_MANAGER.get();
    }

    static void setUserCredentials(UserCredentials userCredentials) {
        USER_MANAGER.set(userCredentials);
    }

    public static void unload() {
        USER_MANAGER.remove();
    }
}
