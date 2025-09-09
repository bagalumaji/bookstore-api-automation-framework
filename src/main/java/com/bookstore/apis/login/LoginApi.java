package com.bookstore.apis.login;

import com.bookstore.client.ApiClient;
import com.bookstore.constants.ApiConstants;
import com.bookstore.constants.LoginConstants;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.pojo.LoginResponse;
import com.bookstore.reports.ExtentReportLogger;
import com.bookstore.specs.ApiRequestSpecs;
import com.bookstore.specs.ApiResponseSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

import static com.bookstore.configs.BookstoreConfigReader.config;

public class LoginApi {

    public Response login(UserCredentials userCredentials) {
        ExtentReportLogger.info("Requesting Method : POST");
        ExtentReportLogger.info("Performing login with credentials: "+userCredentials);
        RequestSpecification req=ApiRequestSpecs.getRequestSpec().basePath(config().loginEndpoint()).body(userCredentials);
        ResponseSpecification res = ApiResponseSpecs.getSuccessResponseSpec();
        return ApiClient.post(req,res);
    }
    public Response login(UserCredentials userCredentials, ResponseSpecification res) {
        ExtentReportLogger.info("Requesting Method : POST");
        ExtentReportLogger.info("Performing login with credentials: "+userCredentials);
        RequestSpecification req=ApiRequestSpecs.getRequestSpec().basePath(config().loginEndpoint()).body(userCredentials);
        return ApiClient.post(req,res);
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
        Assert.assertEquals(loginResponse.getToken_type(), ApiConstants.TOKEN_TYPE, LoginConstants.LOGIN_WAS_NOT_SUCCESSFUL);
    }
}
