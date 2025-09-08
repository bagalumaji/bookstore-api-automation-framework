package com.bookstore.utils;

public final class FakerDataUtil {
    private FakerDataUtil() {
    }

    public static String getEmail() {
        return FakerUtil.getEmail();
    }

    public static int getPositiveId() {
        return FakerUtil.getPositiveId();
    }
}
