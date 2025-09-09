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
import com.bookstore.specs.ApiRequestSpecs;
import com.bookstore.specs.ApiResponseSpecs;
import com.bookstore.specs.ApiRequestResponseSpec;
import com.bookstore.user.UserManager;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.bookstore.configs.BookstoreConfigReader.config;
import static com.bookstore.enums.ApiStatusCodes.BAD_REQUEST;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class LoginTests {

    @Bookstore(author = "Umaji",category = "login")
    @Test(description = "login successful with valid credentials test", groups = {"smoke","login"}, priority = 1)
    public void testLogin_ValidCredentials_Positive() {
        ApiRequestResponseSpec apiRequestResponseSpec = ApiRequestResponseSpec.builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec())
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Response response = new LoginApi().login(UserManager.getUserCredentials(), apiRequestResponseSpec);

        ValidationUtility.validateStatusCode(response,  ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        LoginApi.validateLoginResponse(loginResponse);
    }

    @Bookstore(author = "Umaji",category = "login")
    @Test(groups = {"regression","login"}, priority = 2, dataProvider = "invalidUsers", dataProviderClass = UserDataProvider.class, description = "Testing login with invalid credentials")
    public void loginInvalidCredentialsTest(UserCredentials invalidLogin) {
        ApiRequestResponseSpec apiRequestResponseSpec = ApiRequestResponseSpec.builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec())
                .respSpec(ApiResponseSpecs.getBadRequestResponseSpec())
                .build();

        Response response = new LoginApi().login(invalidLogin, apiRequestResponseSpec);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.BAD_REQUEST.getCode());
        ValidationUtility.validateErrorResponse(response,  ApiStatusCodes.BAD_REQUEST.getCode(), LoginConstants.INCORRECT_EMAIL_OR_PASSWORD);
    }


    @Bookstore(author = "Umaji",category = "login")
    @Test(groups = {"regression","login"}, priority = 3, dataProvider = "validUsers", dataProviderClass = UserDataProvider.class, description = "Test Login for valid Credentials Test")
    public void testLogin_validCredentialsTest(UserCredentials validLogin) {
        ApiRequestResponseSpec apiRequestResponseSpec = ApiRequestResponseSpec.builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec())
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Response res = SignupApi.signUp(validLogin);
        SignupApi.validateSignup(res);

        Response response = new LoginApi().login(validLogin, apiRequestResponseSpec);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        LoginApi.validateLoginResponse(loginResponse);
    }

    @Bookstore(author = "Umaji",category = "login")
    @Test(groups = {"regression","login"}, priority =4, description = "login with Empty Payload test")
    public void testLogin_EmptyPayload_Negative() {
        ApiRequestResponseSpec apiRequestResponseSpec = ApiRequestResponseSpec.builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec())
                .respSpec(ApiResponseSpecs.getBadRequestResponseSpec())
                .build();

        Response response = new LoginApi().login(UserCredentials.builder().build(), apiRequestResponseSpec);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.BAD_REQUEST.getCode());
        ValidationUtility.validateErrorResponse(response, BAD_REQUEST.getCode(), LoginConstants.INCORRECT_EMAIL_OR_PASSWORD);
    }

    @Bookstore(author = "Umaji",category = "login")
    @Test(groups = {"regression","login"}, priority = 5, description = "login Malformed Json test")
    public void testLogin_MalformedJson_Negative() {
        ApiRequestResponseSpec apiRequestResponseSpec = ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec())
                .respSpec(ApiResponseSpecs.getBadRequestResponseSpec())
                .build();

        UserCredentials malformedRequest = UserCredentials.builder().build();
        Response response = new LoginApi().login(malformedRequest, apiRequestResponseSpec);
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.BAD_REQUEST.getCode());
    }

    @Bookstore(author = "Umaji",category = "login")
    @Test(groups = {"performance","login"}, priority = 6, description = "Login Response Time Test")
    public void testLogin_ResponseTime() {
        ApiRequestResponseSpec apiRequestResponseSpec = ApiRequestResponseSpec.builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec())
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        UserCredentials userCredentials = UserCredentials.builder()
                .email(config().email())
                .password(config().password())
                .build();

        Response response = new LoginApi().login(userCredentials, apiRequestResponseSpec);

        ValidationUtility.validateResponseTime(response, BookstoreConfigReader.config().requestTimeout());
    }
}
