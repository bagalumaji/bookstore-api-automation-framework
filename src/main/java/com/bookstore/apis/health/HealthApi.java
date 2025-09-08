package com.bookstore.apis.health;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import io.restassured.response.Response;

public class HealthApi {
    public Response getHealthStatus() {
        return ApiClient.get(BookstoreConfigReader.config().healthEndPoint());
    }
}
