package com.bookstore.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static com.bookstore.enums.StatusTypes.OK;
//todo
public class ApiResponseSpecs {
//    public static ResponseSpecification ok200Spec() {
//        return new ResponseSpecBuilder()
//                .expectStatusCode(OK.getCode())
//                .expectStatusLine(OK.getMessage())
//                .log(LogDetail.ALL)
//                .build();
//    }

    public static ResponseSpecification getSuccessResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_OK)
                .expectContentType(ContentType.JSON)
                .build();
    }

//    public static ResponseSpecification getCreatedResponseSpec() {
//        return new ResponseSpecBuilder()
//                .addResponseSpecification(ok200Spec())
//                .expectStatusCode(HttpStatus.SC_CREATED)
//                .expectContentType(ContentType.JSON)
//                .build();
//    }

    public static ResponseSpecification getBadRequestResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getUnauthorizedResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_UNAUTHORIZED)
                .expectContentType(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification getForbiddenResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_FORBIDDEN)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getNotFoundResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification getNoContentResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_NO_CONTENT)
                .build();
    }
}
