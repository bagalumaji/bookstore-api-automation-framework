package com.bookstore.tests;

import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.signup.SignupApi;
import com.bookstore.dataproviders.UserDataProvider;
import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.UserCredentials;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class SignupTests {
    @Bookstore(author = "Umaji",category = "signup")
    @Test(priority = 1,groups = {"smoke"}, description = "verify Create User Test")
    public void verifyCreateUserTest() {
        // Arrange
        UserCredentials signupPojo = UserCredentialsFactory.createSignupDataWithNewEmail();

        //Act
        Response response = SignupApi.signUp(signupPojo);

        //Assert
        SignupApi.validateSignup(response);
    }

    @Bookstore(author = "Umaji",category = "signup")
    @Test(groups = {"smoke"}, priority = 2, dataProvider = "validUsers", dataProviderClass = UserDataProvider.class,description = "verifying signup with valid user data Test")
    public void verifySignupForValidUserTest(UserCredentials validUser) {
        Response response = SignupApi.signUp(validUser);
        SignupApi.validateSignup(response);
    }
}

