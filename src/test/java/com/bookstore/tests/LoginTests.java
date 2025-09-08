package com.bookstore.tests;

import com.bookstore.apis.login.LoginApi;
import com.bookstore.apis.signup.SignupApi;
import com.bookstore.dataproviders.TestDataProvider;
import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.LoginResponse;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.user.UserManager;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.bookstore.configs.BookstoreConfigReader.config;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class LoginTests {

    @Test(description = "login successful with valid credentials test", groups = {"smoke", "auth"}, priority = 1)
    public void testLogin_ValidCredentials_Positive() {

//        UserCredentials signupPojo = UserCredentialsFactory.createSignupDataWithNewEmail();
//        Response res =SignupApi.signUp(signupPojo);
//        SignupApi.validateSignup(res);

        Response response = new LoginApi().login(UserManager.getUserCredentials());

        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        LoginResponse loginResponse = response.as(LoginResponse.class);
        ValidationUtility.validateLoginResponse(loginResponse);
    }

    @Test(groups = {"regression", "auth"}, priority = 2, dataProvider = "invalidUsers", dataProviderClass = TestDataProvider.class,description = "Testing login with invalid credentials")
    public void loginInvalidCredentialsTest(UserCredentials invalidLogin) {
        Response response = new LoginApi().login(invalidLogin);

        ValidationUtility.validateStatusCode(response, 400);
        ValidationUtility.validateErrorResponse(response, 400, "Incorrect email or password");
    }


    @Test(groups = {"regression", "auth"}, priority = 2, dataProvider = "validUsers",
            dataProviderClass = TestDataProvider.class,description = "Test Login for valid Credentials Test")
    public void testLogin_validCredentialsTest(UserCredentials validLogin) {

        Response res =SignupApi.signUp(validLogin);
        SignupApi.validateSignup(res);

        Response response = new LoginApi().login(validLogin);

        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        LoginResponse loginResponse = response.as(LoginResponse.class);
        ValidationUtility.validateLoginResponse(loginResponse);
    }


    @Test(groups = {"regression", "auth"}, priority = 5,description = "login with Empty Payload test")
    public void testLogin_EmptyPayload_Negative() {
        Response response = new LoginApi().login(UserCredentials.builder().build());

        ValidationUtility.validateStatusCode(response, 400);
        ValidationUtility.validateErrorResponse(response, 400, "Incorrect email or password");
    }

    @Test(groups = {"regression", "auth"}, priority = 6,description = "login Malformed Json test")
    public void testLogin_MalformedJson_Negative() {
        UserCredentials malformedRequest = UserCredentials.builder().build();
        Response response = new LoginApi().login(malformedRequest);
        ValidationUtility.validateStatusCode(response, 400);

    }

    @Test(groups = {"performance", "auth"}, priority = 7,description = "Login Response Time Test")
    public void testLogin_ResponseTime() {

        UserCredentials userCredentials = UserCredentials.builder()
                .email(config().email())
                .password(config().password())
                .build();

        Response response = new LoginApi().login(userCredentials);

        ValidationUtility.validateResponseTime(response, 5000);
    }
}
