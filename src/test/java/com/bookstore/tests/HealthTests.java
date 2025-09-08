package com.bookstore.tests;

import com.bookstore.apis.health.HealthApi;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class HealthTests {

    @Test(groups = {"smoke", "health"}, priority = 1)
    public void testHealthEndpoint_Positive() {
        Response response = new HealthApi().getHealthStatus();

        ValidationUtility.validateHealthResponse(response);
    }

    @Test(groups = {"smoke", "health"}, priority = 2)
    public void testHealthEndpoint_ResponseTime() {
        Response response =new HealthApi().getHealthStatus();

        ValidationUtility.validateResponseTime(response, 5000);
    }

    @Test(groups = {"regression", "health"}, priority = 3)
    public void testHealthEndpoint_ContentType() {
        Response response = new HealthApi().getHealthStatus();

        ValidationUtility.validateContentType(response, "application/json");
    }
}