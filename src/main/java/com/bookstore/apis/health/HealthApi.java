package com.bookstore.apis.health;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.reports.ExtentReportLogger;
import io.restassured.response.Response;

import static com.bookstore.utils.ValidationUtility.*;

public class HealthApi {
    public Response getHealthStatus() {
        ExtentReportLogger.info("Requesting Method : GET");
        ExtentReportLogger.info("Checking health of API: ");
        return ApiClient.get(BookstoreConfigReader.config().healthEndPoint());
    }
    public static void validateHealthResponse(Response response) {
        ExtentReportLogger.logResponseInfo(response);
        validateStatusCode(response, 200);
        validateResponseTime(response, 5000);
        validateContentType(response, "application/json");
        validateResponseNotEmpty(response);
    }
}