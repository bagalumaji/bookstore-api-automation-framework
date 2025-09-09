package com.bookstore.specs;

import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.reports.ExtentReportLogger;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class ApiRequestSpecs {
    private ApiRequestSpecs() {
    }

    public static RequestSpecification getRequestSpec() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(BookstoreConfigReader.config().baseUri())
                .setContentType(JSON)
                .build();
        ExtentReportLogger.logRequestInfo(requestSpecification);
        return requestSpecification;
    }

    public static RequestSpecification getRequestSpec(int pathParam) {
        RequestSpecification requestSpecification = getRequestSpec().pathParam("id",pathParam);
        ExtentReportLogger.logRequestInfo(requestSpecification);
        return requestSpecification;
    }

    public static RequestSpecification getRequestSpecWithAuth(String token) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader("Authorization", "Bearer " + token)
                .build();
        ExtentReportLogger.logRequestInfo(requestSpecification);
        return requestSpecification;
    }

    public static RequestSpecification getRequestSpecWithAuth(String token, int pathParam) {
        RequestSpecification requestSpecification = getRequestSpecWithAuth(token).pathParam("id", pathParam);
        ExtentReportLogger.logRequestInfo(requestSpecification);
        return requestSpecification;
    }
}
