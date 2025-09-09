package com.bookstore.dataproviders;

import com.bookstore.factory.BookFactory;
import com.bookstore.utils.FakerDataUtil;
import org.testng.annotations.DataProvider;

import static com.bookstore.factory.BookFactory.createValidBook;

public class BookDataProvider {

    @DataProvider(name = "validBooks")
    public static Object[][] validBooksData() {
        return new Object[][] {
                {createValidBook("The Great Gatsby", "F. Scott Fitzgerald", 1925, "A classic American novel")},
                {createValidBook("To Kill a Mockingbird", "Harper Lee", 1960, "A story of racial injustice")},
                {createValidBook("1984", "George Orwell", 1949, "A dystopian social science fiction novel")},
                {createValidBook("Pride and Prejudice", "Jane Austen", 1813, "A romantic novel of manners")},
                {createValidBook("The Catcher in the Rye", "J.D. Salinger", 1951, "A coming-of-age story")}
        };
    }

    @DataProvider(name = "updateBookData")
    public static Object[][] updateBookData() {
        return new Object[][] {
                {createValidBook("Updated Book Title", "Updated Author", 2024, "Updated summary")},
                {createValidBook("Another Updated Title", "Another Author", 2023, "Another summary")},
                {createValidBook("Final Update", "Final Author", 2022, "Final summary")}
        };
    }
    @DataProvider(name = "bookTestData")
    public Object[][] getBookTestData() {
        return new Object[][] {
                {BookFactory.createValidBook(FakerDataUtil.getBookTitle(), FakerDataUtil.getAuthorName(), FakerDataUtil.getPublishedYear(), FakerDataUtil.getBookSummary())},
                {BookFactory.createValidBook(FakerDataUtil.getBookTitle(), FakerDataUtil.getAuthorName(), FakerDataUtil.getPublishedYear(), FakerDataUtil.getBookSummary())},
                {BookFactory.createValidBook(FakerDataUtil.getBookTitle(), FakerDataUtil.getAuthorName(), FakerDataUtil.getPublishedYear(), FakerDataUtil.getBookSummary())}
        };
    }


}
