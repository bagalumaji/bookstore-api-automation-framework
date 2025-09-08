package com.bookstore.utils;

import net.datafaker.Faker;

public final class FakerUtil {
    private FakerUtil() {
    }

    private static final Faker faker = new Faker();

    static String getEmail() {
        return faker.internet().emailAddress();
    }

    static int getPositiveId() {
        return faker.number().numberBetween(1, 10000);
    }

    static String getPassword() {
        return "Bookstore@123";
    }
}
