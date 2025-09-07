package com.bookstore.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DemoTest {

    @Test
    public void demoTest() {
        String baseURI="http://127.0.0.1:8000";
        String payLoad = "{\n" +
                "  \"id\": 0,\n" +
                "  \"email\": \"string\",\n" +
                "  \"password\": \"string\"\n" +
                "}";

        Response response=given()
               .baseUri(baseURI)
                .contentType(ContentType.JSON)
                .body(payLoad)
                .when()
                .post("/signup");

        response.prettyPrint();
    }
}
