package com.bookstore.token;

public final class TokenManager {
    private static final ThreadLocal<String> ACCESS_TOKEN = new ThreadLocal<>();
    public static String getToken() {
        return ACCESS_TOKEN.get();
    }
    static void setAccessToken(String accessToken) {
        ACCESS_TOKEN.set(accessToken);
    }
    public static void unload() {
        ACCESS_TOKEN.remove();
    }
}
