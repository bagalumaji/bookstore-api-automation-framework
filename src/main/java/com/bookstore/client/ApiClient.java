package com.bookstore.client;

import com.bookstore.reports.ExtentReportLogger;
import com.bookstore.specs.ApiRequestSpecs;
import com.bookstore.specs.ApiResponseSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

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
                .spec(ApiRequestSpecs.getRequestSpec(token))
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
                .when()
                .post(endpoint)
                .then()
                .spec(ApiResponseSpecs.getSuccessResponseSpec())
                .extract()
                .response();
    }


    public static Response get(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        Response response= prepareRequest(requestSpecification).get();
        return validateAndExtractResponse(response,responseSpecification);
    }

    public static Response post(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        Response response= prepareRequest(requestSpecification).post();
        return validateAndExtractResponse(response,responseSpecification);
    }
    public static Response put(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        Response response= prepareRequest(requestSpecification).put();
        return validateAndExtractResponse(response,responseSpecification);
    }
    public static Response delete(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
        Response response= prepareRequest(requestSpecification).delete();
        return validateAndExtractResponse(response,responseSpecification);
    }

    private static RequestSpecification prepareRequest(RequestSpecification requestSpecification){
        return given()
                .spec(requestSpecification)
                .log()
                .ifValidationFails()
                .when();
    }
    private static Response validateAndExtractResponse(Response response, ResponseSpecification responseSpecification){
        ExtentReportLogger.logResponseInfo(response);
        return response
                .then()
                .spec(responseSpecification)
                .log()
                .ifValidationFails()
                .extract()
                .response();
    }
}