package com.bookstore.client;

import com.bookstore.specs.ApiRequestSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public final class ApiClient {

    public static Response get(String endpoint) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .when()
                .get(endpoint);
    }

    public static Response getWithAuth(String endpoint, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .when()
                .get(endpoint);
    }

    public static Response post(String endpoint, Object requestBody) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public static Response postWithAuth(String endpoint, Object requestBody, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public static Response put(String endpoint, Object requestBody) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public static Response putWithAuth(String endpoint, Object requestBody, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public static Response delete(String endpoint) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .when()
                .delete(endpoint);
    }

    public static Response deleteWithAuth(String endpoint, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .when()
                .delete(endpoint);
    }

    public static Response patch(String endpoint, Object requestBody) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpec())
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    public static Response patchWithAuth(String endpoint, Object requestBody, String token) {
        return given()
                .spec(ApiRequestSpecs.getRequestSpecWithAuth(token))
                .body(requestBody)
                .when()
                .patch(endpoint);
    }
}
