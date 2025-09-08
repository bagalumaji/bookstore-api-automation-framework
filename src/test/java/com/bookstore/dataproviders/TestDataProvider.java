package com.bookstore.dataproviders;

import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.Book;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.utils.FakerDataUtil;
import org.testng.annotations.DataProvider;


public class TestDataProvider {
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

    @DataProvider(name = "invalidBooks")
    public static Object[][] invalidBooksData() {
        return new Object[][] {
                {createInvalidBook(null, "Author Name", 2023, "Summary")},
                {createInvalidBook("", "Author Name", 2023, "Summary")},
                {createInvalidBook("Book Name", null, 2023, "Summary")},
                {createInvalidBook("Book Name", "", 2023, "Summary")},
                {createInvalidBook("Book Name", "Author Name", 0, "Summary")},
                {createInvalidBook("Book Name", "Author Name", -1, "Summary")}
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

    @DataProvider(name = "validUsers")
    public static Object[][] validUsersData() {
        return new Object[][] {
                {UserCredentialsFactory.createSignupDataWithNewEmail()},
                {UserCredentialsFactory.createSignupDataWithNewEmail()},
                {UserCredentialsFactory.createSignupDataWithNewEmail()}
        };
    }

    @DataProvider(name = "invalidUsers")
    public static Object[][] invalidUsersData() {
        return new Object[][] {
                {UserCredentials.builder().build()},
                {UserCredentials.builder().email("").password("").build()},
                {UserCredentials.builder().email("").password(BookstoreConfigReader.config().password()).build()},
                {UserCredentials.builder().email(BookstoreConfigReader.config().email()).password("").build()},
                {UserCredentials.builder().email("BookstoreConfigReader.config().email()").password("12345").build()}
        };
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

    public static Book createInvalidBook(String name, String author, Integer year, String summary) {
        return Book.builder()
                .id(FakerDataUtil.getPositiveId())
                .name(name)
                .author(author)
                .published_year(year)
                .book_summary(summary)
                .build();
    }

    public static Book createRandomBook() {
        return Book.builder()
                .id(FakerDataUtil.getPositiveId())
                .name("Random Book " +FakerDataUtil.getPositiveId())
                .author("Random Author " + FakerDataUtil.getPositiveId())
                .published_year(1900 + FakerDataUtil.getPositiveId())
                .book_summary("Random summary for testing " + FakerDataUtil.getPositiveId())
                .build();
    }

    public static UserCredentials createValidLoginRequest(String username, String password) {
        return UserCredentials.builder()
                .email(username)
                .password(password)
                .build();
    }

}
