package com.bookstore.tests;

import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.login.LoginApi;
import com.bookstore.apis.signup.SignupApi;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.constants.ApiConstants;
import com.bookstore.constants.LoginConstants;
import com.bookstore.dataproviders.UserDataProvider;
import com.bookstore.enums.ApiStatusCodes;
import com.bookstore.pojo.LoginResponse;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.specs.ApiResponseSpecs;
import com.bookstore.user.UserManager;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.bookstore.configs.BookstoreConfigReader.config;
import static com.bookstore.constants.AnnotationConstants.LOGIN;
import static com.bookstore.constants.AnnotationConstants.UMAJI;
import static com.bookstore.enums.ApiStatusCodes.BAD_REQUEST;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class LoginTests {

    @Bookstore(author = UMAJI,category = LOGIN)
    @Test(description = "login successful with valid credentials test", groups = {"smoke","login"}, priority = 1)
    public void testLogin_ValidCredentials_Positive() {
      Response response = new LoginApi().login(UserManager.getUserCredentials(), ApiResponseSpecs.getSuccessResponseSpec());

        ValidationUtility.validateStatusCode(response,  ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        LoginApi.validateLoginResponse(loginResponse);
    }

    @Bookstore(author = UMAJI,category = LOGIN)
    @Test(groups = {"regression","login"}, priority = 2, dataProvider = "invalidUsers", dataProviderClass = UserDataProvider.class, description = "Testing login with invalid credentials")
    public void loginInvalidCredentialsTest(UserCredentials invalidLogin) {
        Response response = new LoginApi().login(invalidLogin, ApiResponseSpecs.getBadRequestResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.BAD_REQUEST.getCode());
        ValidationUtility.validateErrorResponse(response,  ApiStatusCodes.BAD_REQUEST.getCode(), LoginConstants.INCORRECT_EMAIL_OR_PASSWORD);
    }


    @Bookstore(author = UMAJI,category = LOGIN)
    @Test(groups = {"regression","login"}, priority = 3, dataProvider = "validUsers", dataProviderClass = UserDataProvider.class, description = "Test Login for valid Credentials Test")
    public void testLogin_validCredentialsTest(UserCredentials validLogin) {

        Response res = SignupApi.signUp(validLogin);
        SignupApi.validateSignup(res);

        Response response = new LoginApi().login(validLogin, ApiResponseSpecs.getSuccessResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        LoginApi.validateLoginResponse(loginResponse);
    }

    @Bookstore(author = UMAJI,category = LOGIN)
    @Test(groups = {"regression","login"}, priority =4, description = "login with Empty Payload test")
    public void testLogin_EmptyPayload_Negative() {
        Response response = new LoginApi().login(UserCredentials.builder().build(), ApiResponseSpecs.getBadRequestResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.BAD_REQUEST.getCode());
        ValidationUtility.validateErrorResponse(response, BAD_REQUEST.getCode(), LoginConstants.INCORRECT_EMAIL_OR_PASSWORD);
    }

    @Bookstore(author = UMAJI,category = LOGIN)
    @Test(groups = {"regression","login"}, priority = 5, description = "login Malformed Json test")
    public void testLogin_MalformedJson_Negative() {
        UserCredentials malformedRequest = UserCredentials.builder().build();
        Response response = new LoginApi().login(malformedRequest, ApiResponseSpecs.getBadRequestResponseSpec());
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.BAD_REQUEST.getCode());
    }

    @Bookstore(author = UMAJI,category = LOGIN)
    @Test(groups = {"performance","login"}, priority = 6, description = "Login Response Time Test")
    public void testLogin_ResponseTime() {
        UserCredentials userCredentials = UserCredentials.builder()
                .email(config().email())
                .password(config().password())
                .build();

        Response response = new LoginApi().login(userCredentials, ApiResponseSpecs.getSuccessResponseSpec());

        ValidationUtility.validateResponseTime(response, BookstoreConfigReader.config().requestTimeout());
    }
}
