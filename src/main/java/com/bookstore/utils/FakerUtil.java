package com.bookstore.utils;

import net.datafaker.Faker;

import java.util.Random;

public final class FakerUtil {
    private FakerUtil() {
    }

    private static final Random random = new Random();
    private static final Faker faker = new Faker();


    static String getEmail() {
        return faker.internet().emailAddress();
    }

    static String getPassword() {
        return "Bookstore@123";
    }

    static String getBookTitle() {
        return faker.book().title();
    }

    static String getAuthorName() {
        return faker.book().author();
    }

    static int getPublishedYear() {
        int currentYear = java.time.Year.now().getValue();
        return faker.number().numberBetween(1900, currentYear + 1);
    }

    static String getBookSummary() {
        int sentenceCount = faker.number().numberBetween(3, 6);
        StringBuilder summary = new StringBuilder();

        for (int i = 0; i < sentenceCount; i++) {
            summary.append(faker.lorem().sentence());
            if (i < sentenceCount - 1) {
                summary.append(" ");
            }
        }

        return summary.toString();
    }

    static String getBookSummaryAlternative() {
        String[] genres = {"mystery", "romance", "thriller", "fantasy", "science fiction",
                "historical fiction", "biography", "adventure", "drama"};
        String genre = genres[random.nextInt(genres.length)];

        return String.format("A captivating %s that follows %s through %s. %s",
                genre,
                faker.name().fullName(),
                faker.lorem().words(5),
                faker.lorem().sentence(15));
    }

    static int getPositiveId() {
        return faker.number().numberBetween(1, 999999);
    }
}
