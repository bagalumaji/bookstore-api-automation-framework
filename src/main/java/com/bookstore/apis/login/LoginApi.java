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
        return login(username, password)
                .as(LoginResponse.class)
                .getAccess_token();
    }

    public String getAuthToken(UserCredentials userCredentials) {
        return login(userCredentials)
                .as(LoginResponse.class)
                .getAccess_token();
    }
}
