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

    public static String getBookTitle() {
        return FakerUtil.getBookTitle();
    }
    public static String getAuthorName() {
        return FakerUtil.getAuthorName();
    }
    public static int getPublishedYear() {
       return FakerUtil.getPublishedYear();
    }
    public static String getBookSummary() {
       return FakerUtil.getBookSummary();
    }
    public static String getBookSummaryAlternative() {
       return FakerUtil.getBookSummaryAlternative();
    }

}
