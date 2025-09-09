package com.bookstore.utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class ValidationUtility {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
    }

    public static void validateResponseTime(Response response, long maxResponseTime) {
        long responseTime = response.getTime();
        Assert.assertTrue(responseTime <= maxResponseTime,
                "Response time exceeded. Expected: <= " + maxResponseTime + "ms, Actual: " + responseTime + "ms");
    }

    public static void validateContentType(Response response, String expectedContentType) {
        String actualContentType = response.getContentType();
        Assert.assertTrue(actualContentType.contains(expectedContentType),
                "Content type mismatch. Expected to contain: " + expectedContentType + ", Actual: " + actualContentType);
    }

    public static void validateResponseNotEmpty(Response response) {
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body is null");
        Assert.assertFalse(responseBody.trim().isEmpty(), "Response body is empty");
    }

     public static void validateErrorResponse(Response response, int expectedStatusCode, String expectedMessageContains) {
        validateStatusCode(response, expectedStatusCode);

        String actualMessage = response.jsonPath().getString("detail");
        Assert.assertNotNull(actualMessage, "Error message is null");

        Assert.assertEquals(actualMessage, expectedMessageContains,
                "Error message doesn't contain expected text. Expected to contain: '" + expectedMessageContains +
                        "', Actual: '" + actualMessage + "'");
    }
}
