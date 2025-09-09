package com.bookstore.token;

import com.bookstore.apis.login.LoginApi;
import com.bookstore.pojo.UserCredentials;

import java.util.Objects;

public final class Token {
    private Token() {}
    public static void createToken(UserCredentials userCredentials) {
        if(Objects.isNull(TokenManager.getToken())){
            LoginApi loginApi=new LoginApi();
            TokenManager.setAccessToken(loginApi.getAuthToken(userCredentials));
        }
    }
}
