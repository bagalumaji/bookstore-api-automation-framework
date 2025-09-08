package com.bookstore.apis.signup;

import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.specs.ApiRequestSpecs;
import com.bookstore.specs.ApiResponseSpecs;
import io.restassured.response.Response;

import static com.bookstore.constants.SignupConstants.MESSAGE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public final class SignupApi {
    private SignupApi() {
    }

    public static Response signUpApi(UserCredentials signupRequestData) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .body(signupRequestData)
                .log()
                .all()
                .when()
                .post(BookstoreConfigReader.config().signupEndPoint())
                .then()
                .log()
                .all()
                .extract()
                .response();
    }


    public static void validateSignApi(Response response) {
        response
                .then()
                .spec(ApiResponseSpecs.ok200Spec())
                .body("message",equalTo(MESSAGE));
    }
}
