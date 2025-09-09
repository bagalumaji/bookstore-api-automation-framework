package com.bookstore.tests;

import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.health.HealthApi;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.constants.ApiConstants;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static com.bookstore.constants.AnnotationConstants.HEALTH;
import static com.bookstore.constants.AnnotationConstants.UMAJI;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class HealthTests {

    @Bookstore(author = UMAJI,category = HEALTH)
    @Test(groups = {"smoke","health"}, priority = 1,description = "verify Health Test")
    public void testHealthEndpoint_Positive() {
        Response response = new HealthApi().getHealthStatus();
        HealthApi.validateHealthResponse(response);
    }

    @Bookstore(author = UMAJI,category = HEALTH)
    @Test(groups = {"smoke","health"}, priority = 2,description = "verify Health Response Test")
    public void testHealthEndpoint_ResponseTime() {
        Response response =new HealthApi().getHealthStatus();
        ValidationUtility.validateResponseTime(response, BookstoreConfigReader.config().requestTimeout());
    }

    @Bookstore(author = UMAJI,category = HEALTH)
    @Test(groups = {"regression","health"}, priority = 3,description = "verify Health Content Type Test")
    public void testHealthEndpoint_ContentType() {
        Response response = new HealthApi().getHealthStatus();
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);
    }
}