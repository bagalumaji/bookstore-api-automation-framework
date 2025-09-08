package com.bookstore.user;

import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.UserCredentials;

import java.util.Objects;

public final class User {
    private User() {
    }

    public static void createUser() {
        if (Objects.isNull(UserManager.getUserCredentials())) {
           UserManager.setUserCredentials(UserCredentialsFactory.createSignupDataWithNewEmail());
        }
    }
}
