package com.bookstore.factory;

import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.utils.FakerDataUtil;

public final class UserCredentialsFactory {
    private UserCredentialsFactory() {
    }

    public static UserCredentials createSignupDataWithNewEmail() {
        return UserCredentials
                .builder()
                .id(FakerDataUtil.getPositiveId())
                .email(FakerDataUtil.getEmail())
                .password(BookstoreConfigReader.config().password())
                .build();
    }

    public static UserCredentials createSignupDataWithExistingEmail() {
        return UserCredentials
                .builder()
                .id(FakerDataUtil.getPositiveId())
                .email(BookstoreConfigReader.config().email())
                .password(BookstoreConfigReader.config().password())
                .build();
    }

}
