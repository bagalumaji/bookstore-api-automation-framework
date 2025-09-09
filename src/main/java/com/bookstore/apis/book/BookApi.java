package com.bookstore.apis.book;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.pojo.Book;
import com.bookstore.specs.ApiRequestResponseSpec;
import io.restassured.response.Response;
import org.testng.Assert;

public class BookApi {
    public Response getAllBooks(String token) {
        return ApiClient.getWithAuth(BookstoreConfigReader.config().booksEndPoint(), token);
    }

    public Response getBookById(ApiRequestResponseSpec apiRequestResponseSpec) {
        return ApiClient.getWithAuth(apiRequestResponseSpec);
    }

    public Response createBook(Book book, String token) {
        return ApiClient.postWithAuth(BookstoreConfigReader.config().booksEndPoint(), book, token);
    }

    public Response createBook(Book book, ApiRequestResponseSpec apiRequestResSpec) {
        return ApiClient.postWithAuth(BookstoreConfigReader.config().booksEndPoint(), book,apiRequestResSpec);
    }

    public Response updateBook(Book book, ApiRequestResponseSpec apiRequestResSpec) {
        return ApiClient.put(BookstoreConfigReader.config().booksEndPointWithParamId(), book, apiRequestResSpec);
    }

    public Response deleteBook(ApiRequestResponseSpec apiRequestResSpec) {
        return ApiClient.delete(apiRequestResSpec);
    }

    public Book createBookAndReturn(Book book, ApiRequestResponseSpec apiRequestResSpec) {
        Response response = createBook(book, apiRequestResSpec);
        response.prettyPrint();
        if (response.getStatusCode() == 200) {
            return response.as(Book.class);
        }
        throw new RuntimeException("Failed to create book. Status: " + response.getStatusCode());
    }

    public Book updateBookAndReturn(Book book, ApiRequestResponseSpec apiRequestResSpec) {
        Response response = updateBook(book,apiRequestResSpec);
        if (response.getStatusCode() == 200) {
            return response.as(Book.class);
        }
        throw new RuntimeException("Failed to update book. Status: " + response.getStatusCode());
    }
    public static void validateBookCreation(Book expectedBook, Book actualBook) {
        Assert.assertNotNull(actualBook, "Created book is null");
        Assert.assertEquals(actualBook.getId(), expectedBook.getId(), "Book ID mismatch during update");
        Assert.assertEquals(actualBook.getName(), expectedBook.getName(), "Book name mismatch");
        Assert.assertEquals(actualBook.getAuthor(), expectedBook.getAuthor(), "Book author mismatch");
        Assert.assertEquals(actualBook.getPublished_year(), expectedBook.getPublished_year(), "Published year mismatch");
        Assert.assertEquals(actualBook.getBook_summary(), expectedBook.getBook_summary(), "Book summary mismatch");
    }

    public static void validateBookUpdate(Book expectedBook, Book actualBook) {
        validateBookCreation(expectedBook, actualBook);
    }
    public static void validateBookResponse(Book book) {
        Assert.assertNotNull(book, "Book response is null");
        Assert.assertNotNull(book.getName(), "Book name is null");
        Assert.assertNotNull(book.getAuthor(), "Book author is null");
        Assert.assertFalse(book.getName().trim().isEmpty(), "Book name is empty");
        Assert.assertFalse(book.getAuthor().trim().isEmpty(), "Book author is empty");
    }
}
