package com.bookstore.client;

import com.bookstore.specs.ApiRequestSpecs;
import com.bookstore.specs.ApiResponseSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
public final class ApiClient {

    public static Response get(String endpoint) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .log()
                .ifValidationFails()
                .when()
                .get(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response getWithAuth(String endpoint, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .log()
                .ifValidationFails()
                .when()
                .get(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response post(String endpoint, Object requestBody) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .body(requestBody)
                //.log()
                //.ifValidationFails()
                .when()
                .post(endpoint)
                .then()
                .spec(ApiResponseSpecs.ok200Spec())
              //  .log()
               // .ifValidationFails()
                .extract()
                .response();
    }

    public static Response postWithAuth(String endpoint, Object requestBody, String token) {
        System.out.println("requestBody = " + requestBody);
        System.out.println("endpoint = " + endpoint);

        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .body(requestBody)
                .log()
                .ifValidationFails()
                .when()
                .post(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object requestBody) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .body(requestBody)
                .log()
                .ifValidationFails()
                .when()
                .put(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response putWithAuth(String endpoint, Object requestBody, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .body(requestBody)
                .log()
                .ifValidationFails()
                .when()
                .put(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .log()
                .ifValidationFails()
                .when()
                .delete(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response deleteWithAuth(String endpoint, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .log()
                .ifValidationFails()
                .when()
                .delete(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response patch(String endpoint, Object requestBody) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .body(requestBody)
                .log()
                .ifValidationFails()
                .when()
                .patch(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }

    public static Response patchWithAuth(String endpoint, Object requestBody, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .body(requestBody)
                .log()
                .ifValidationFails()
                .when()
                .patch(endpoint)
                .then()
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }
}
