package com.bookstore.tests;

import com.bookstore.apis.signup.SignupApi;
import com.bookstore.dataproviders.TestDataProvider;
import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignupTests {

    @Test(priority = 1,groups = {"smoke"}, description = "verify Create User Test")
    public void verifyCreateUserTest() {
        // Arrange
        UserCredentials signupPojo = UserCredentialsFactory.createSignupDataWithNewEmail();

        //Act
        Response response = SignupApi.signUp(signupPojo);

        //Assert
        SignupApi.validateSignup(response);
    }

    @Test(groups = {"smoke"}, priority = 3, dataProvider = "validUsers", dataProviderClass = TestDataProvider.class,description = "Testing signup with valid user data")
    public void verifySignupForValidUserTest(UserCredentials validUser) {
        Response response = SignupApi.signUp(validUser);
        SignupApi.validateSignup(response);
    }
}

