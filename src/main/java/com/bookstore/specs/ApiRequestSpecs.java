package com.bookstore.specs;

import com.bookstore.configs.BookstoreConfigReader;
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
    public static RequestSpecification getRequestSpecWithAuth(String token) {
        return new RequestSpecBuilder()
                .addRequestSpecification(getRequestSpec())
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
