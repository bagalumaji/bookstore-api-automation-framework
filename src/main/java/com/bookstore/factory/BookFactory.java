package com.bookstore.factory;

import com.bookstore.pojo.Book;
import com.bookstore.utils.FakerDataUtil;

public final class BookFactory {
    private BookFactory() {}
    public static Book createRandomBook() {
        return Book.builder()
                .id(FakerDataUtil.getPositiveId())
                .name("Random Book " +FakerDataUtil.getPositiveId())
                .author("Random Author " + FakerDataUtil.getPositiveId())
                .published_year(1900 + FakerDataUtil.getPositiveId())
                .book_summary("Random summary for testing " + FakerDataUtil.getPositiveId())
                .build();
    }
    public static Book createValidBook(String name, String author, int year, String summary) {
        return Book.builder()
                .id(FakerDataUtil.getPositiveId())
                .name(name)
                .author(author)
                .published_year(year)
                .book_summary(summary)
                .build();
    }
}
