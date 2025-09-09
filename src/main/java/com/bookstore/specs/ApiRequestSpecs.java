package com.bookstore.specs;

import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.constants.ApiConstants;
import com.bookstore.reports.ExtentReportLogger;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class ApiRequestSpecs {
    private ApiRequestSpecs() {
    }

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BookstoreConfigReader.config().baseUri())
                .setContentType(JSON)
                .build();
    }

    public static RequestSpecification getRequestSpec(String token) {
        ExtentReportLogger.info("Performing request with token: ");
        return getRequestSpec().header(ApiConstants.AUTHORIZATION, ApiConstants.BEARER + token);
    }

    public static RequestSpecification getRequestSpec(String token, String endpoint, int pathParam) {
        ExtentReportLogger.info("pathParam: " + pathParam);
        return getRequestSpec(token, endpoint).pathParam(ApiConstants.ID, pathParam);
    }

    public static RequestSpecification getRequestSpec(String token, String endpoint) {
        ExtentReportLogger.info("Base Uri: "+BookstoreConfigReader.config().baseUri());
        ExtentReportLogger.info("Endpoint: " + endpoint);
        ExtentReportLogger.info("Performing request with token: ");
        return getRequestSpec(token).basePath(endpoint);
    }
}