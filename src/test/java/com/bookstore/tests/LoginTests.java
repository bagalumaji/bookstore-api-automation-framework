package com.bookstore.tests;

import com.bookstore.apis.login.LoginApi;
import com.bookstore.apis.signup.SignupApi;
import com.bookstore.dataproviders.TestDataProvider;
import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.LoginResponse;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.bookstore.configs.BookstoreConfigReader.config;

public class LoginTests {

    @Test(description = "Login successful with valid credentials", groups = {"smoke", "auth"}, priority = 1)
    public void testLogin_ValidCredentials_Positive() {

        UserCredentials signupPojo = UserCredentialsFactory.createSignupDataWithNewEmail();
        Response res =SignupApi.signUpApi(signupPojo);
        SignupApi.validateSignApi(res);

        Response response = new LoginApi().login(signupPojo);

        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        LoginResponse loginResponse = response.as(LoginResponse.class);
        ValidationUtility.validateLoginResponse(loginResponse);
    }

    @Test(groups = {"regression", "auth"}, priority = 2, dataProvider = "invalidUsers",
            dataProviderClass = TestDataProvider.class)
    public void testLogin_InvalidCredentialsTest(UserCredentials invalidLogin) {
        Response response = new LoginApi().login(invalidLogin);

        ValidationUtility.validateStatusCode(response, 400);
        ValidationUtility.validateErrorResponse(response, 400, "Incorrect email or password");
    }


    @Test(groups = {"regression", "auth"}, priority = 2, dataProvider = "validUsers",
            dataProviderClass = TestDataProvider.class,description = "Test Login for valid Credentials Test")
    public void testLogin_validCredentialsTest(UserCredentials validLogin) {

        Response res =SignupApi.signUpApi(validLogin);
        SignupApi.validateSignApi(res);

        Response response = new LoginApi().login(validLogin);

        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        LoginResponse loginResponse = response.as(LoginResponse.class);
        ValidationUtility.validateLoginResponse(loginResponse);
    }


    @Test(groups = {"regression", "auth"}, priority = 5)
    public void testLogin_EmptyPayload_Negative() {
        Response response = new LoginApi().login(UserCredentials.builder().build());

        ValidationUtility.validateStatusCode(response, 400);
        ValidationUtility.validateErrorResponse(response, 400, "Incorrect email or password");
    }

    @Test(groups = {"regression", "auth"}, priority = 6)
    public void testLogin_MalformedJson_Negative() {
        UserCredentials malformedRequest = UserCredentials.builder().build();
        Response response = new LoginApi().login(malformedRequest);
        ValidationUtility.validateStatusCode(response, 400);

    }

    @Test(groups = {"performance", "auth"}, priority = 7)
    public void testLogin_ResponseTime() {

        UserCredentials userCredentials = UserCredentials.builder()
                .email(config().email())
                .password(config().password())
                .build();

        Response response = new LoginApi().login(userCredentials);

        ValidationUtility.validateResponseTime(response, 5000);
    }
}
