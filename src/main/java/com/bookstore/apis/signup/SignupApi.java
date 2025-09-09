package com.bookstore.apis.signup;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.reports.ExtentReportLogger;
import com.bookstore.specs.ApiResponseSpecs;
import io.restassured.response.Response;

import static com.bookstore.constants.SignupConstants.MESSAGE;
import static org.hamcrest.Matchers.equalTo;

public final class SignupApi {
    private SignupApi() {
    }

    public static Response signUp(UserCredentials signupRequestData) {
        ExtentReportLogger.info("Request Method : POST");
        ExtentReportLogger.info("Body : "+signupRequestData.toString());
        return ApiClient.post(BookstoreConfigReader.config().signupEndPoint(), signupRequestData);
    }


    public static void validateSignup(Response response) {
        ExtentReportLogger.logResponseInfo(response);
        response
                .then()
                .spec(ApiResponseSpecs.getSuccessResponseSpec())
                .body("message",equalTo(MESSAGE));
    }
}
