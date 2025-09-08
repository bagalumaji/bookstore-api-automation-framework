package com.bookstore.apis.login;

import com.bookstore.client.ApiClient;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.pojo.LoginResponse;
import io.restassured.response.Response;

import static com.bookstore.configs.BookstoreConfigReader.config;

public class LoginApi {

    public Response login(UserCredentials userCredentials) {
        return ApiClient.post(config().loginEndpoint(), userCredentials);
    }

    public Response login(String username, String password) {
        UserCredentials userCredentials = UserCredentials.builder()
                .email(username)
                .password(password)
                .build();
        return login(userCredentials);
    }

    public String getAuthToken(String username, String password) {
        Response response = login(username, password);
        if (response.getStatusCode() == 200) {
            LoginResponse loginResponse = response.as(LoginResponse.class);
            return loginResponse.getAccess_token();
        }
        throw new RuntimeException("Failed to get auth token. Status: " + response.getStatusCode() +
                ", Response: " + response.getBody().asString());
    }

    public String getValidAuthToken() {
        return getAuthToken(config().email(), config().password());
    }
}
