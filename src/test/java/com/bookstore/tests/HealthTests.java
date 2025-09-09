package com.bookstore.tests;

import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.health.HealthApi;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.annotations.*;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class HealthTests {

    @Bookstore(author = "Umaji",category = "health")
    @Test(groups = {"smoke"}, priority = 1,description = "verify Health Test")
    public void testHealthEndpoint_Positive() {
        Response response = new HealthApi().getHealthStatus();

        HealthApi.validateHealthResponse(response);
    }

    @Bookstore(author = "Umaji",category = "health")
    @Test(groups = {"smoke"}, priority = 2,description = "verify Health Response Test")
    public void testHealthEndpoint_ResponseTime() {
        Response response =new HealthApi().getHealthStatus();

        ValidationUtility.validateResponseTime(response, 5000);
    }

    @Bookstore(author = "Umaji",category = "health")
    @Test(groups = {"regression"}, priority = 3,description = "verify Health Content Type Test")
    public void testHealthEndpoint_ContentType() {
        Response response = new HealthApi().getHealthStatus();

        ValidationUtility.validateContentType(response, "application/json");
    }
}