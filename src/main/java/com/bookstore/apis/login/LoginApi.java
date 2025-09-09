package com.bookstore.apis.login;

import com.bookstore.client.ApiClient;
import com.bookstore.constants.LoginConstants;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.pojo.LoginResponse;
import com.bookstore.specs.ApiRequestResponseSpec;
import io.restassured.response.Response;
import org.testng.Assert;

import static com.bookstore.configs.BookstoreConfigReader.config;

public class LoginApi {

    public Response login(UserCredentials userCredentials) {
        return ApiClient.post(config().loginEndpoint(), userCredentials);
    }
    public Response login(UserCredentials userCredentials, ApiRequestResponseSpec apiRequestResponseSpec) {
        return ApiClient.post(config().loginEndpoint(), userCredentials, apiRequestResponseSpec);
    }
    public String getAuthToken(UserCredentials userCredentials) {
        return login(userCredentials)
                .as(LoginResponse.class)
                .getAccess_token();
    }
    public static void validateLoginResponse(LoginResponse loginResponse) {
        Assert.assertNotNull(loginResponse, "Login response is null");
        Assert.assertNotNull(loginResponse.getAccess_token(), "Token is null");
        Assert.assertFalse(loginResponse.getAccess_token().trim().isEmpty(), "Token is empty");
        Assert.assertEquals(loginResponse.getToken_type(), LoginConstants.TOKEN_TYPE, LoginConstants.LOGIN_WAS_NOT_SUCCESSFUL);
    }
}
