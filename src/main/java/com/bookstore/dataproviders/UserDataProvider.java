package com.bookstore.dataproviders;

import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.UserCredentials;
import org.testng.annotations.DataProvider;


public class UserDataProvider {
    @DataProvider(name = "validUsers")
    public static Object[][] validUsersData() {
        return new Object[][] {
                {UserCredentialsFactory.createSignupDataWithNewEmail()},
                {UserCredentialsFactory.createSignupDataWithNewEmail()},
                {UserCredentialsFactory.createSignupDataWithNewEmail()}
        };
    }

    @DataProvider(name = "invalidUsers")
    public static Object[][] invalidUsersData() {
        return new Object[][] {
                {UserCredentials.builder().build()},
                {UserCredentials.builder().email("").password("").build()},
                {UserCredentials.builder().email("").password(BookstoreConfigReader.config().password()).build()},
                {UserCredentials.builder().email(BookstoreConfigReader.config().email()).password("").build()},
                {UserCredentials.builder().email("BookstoreConfigReader.config().email()").password("12345").build()}
        };
    }
}
