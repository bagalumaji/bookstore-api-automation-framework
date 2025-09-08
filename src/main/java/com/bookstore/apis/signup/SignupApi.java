package com.bookstore.apis.signup;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.specs.ApiResponseSpecs;
import io.restassured.response.Response;

import static com.bookstore.constants.SignupConstants.MESSAGE;
import static org.hamcrest.Matchers.equalTo;

public final class SignupApi {
    private SignupApi() {
    }

    public static Response signUp(UserCredentials signupRequestData) {
        return ApiClient.post(BookstoreConfigReader.config().signupEndPoint(), signupRequestData);
    }


    public static void validateSignup(Response response) {
        response
                .then()
                .spec(ApiResponseSpecs.ok200Spec())
                .body("message",equalTo(MESSAGE));
    }
}
