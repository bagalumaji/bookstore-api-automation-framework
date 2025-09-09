package com.bookstore.apis.health;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import io.restassured.response.Response;

import static com.bookstore.utils.ValidationUtility.*;

public class HealthApi {
    public Response getHealthStatus() {
        return ApiClient.get(BookstoreConfigReader.config().healthEndPoint());
    }
    public static void validateHealthResponse(Response response) {
        validateStatusCode(response, 200);
        validateResponseTime(response, 5000);
        validateContentType(response, "application/json");
        validateResponseNotEmpty(response);
    }
}
