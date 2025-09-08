package com.bookstore.apis.book;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.pojo.Book;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.List;

public class BookApi {
    public Response getAllBooks(String token) {
        return ApiClient.getWithAuth(BookstoreConfigReader.config().booksEndPoint(), token);
    }

    public Response getBookById(int bookId, String token) {
        String endpoint = BookstoreConfigReader.config().booksEndPoint() + "/" + bookId;
        return ApiClient.getWithAuth(endpoint, token);
    }

    public Response createBook(Book book, String token) {
        return ApiClient.postWithAuth(BookstoreConfigReader.config().booksEndPoint(), book, token);
    }

    public Response updateBook(int bookId, Book book, String token) {
        String endpoint = BookstoreConfigReader.config().booksEndPoint() + "/" + bookId;
        return ApiClient.putWithAuth(endpoint, book, token);
    }

    public Response deleteBook(int bookId, String token) {
        String endpoint = BookstoreConfigReader.config().booksEndPoint() + "/" + bookId;
        return ApiClient.deleteWithAuth(endpoint, token);
    }

    public List<Book> getBooksAsList(String token) {
        Response response = getAllBooks(token);
        if (response.getStatusCode() == 200) {
            Book[] booksArray = response.as(Book[].class);
            return Arrays.asList(booksArray);
        }
        throw new RuntimeException("Failed to get books list. Status: " + response.getStatusCode());
    }

    public Book createBookAndReturn(Book book, String token) {
        Response response = createBook(book, token);
        response.prettyPrint();
        if (response.getStatusCode() == 200) {
            return response.as(Book.class);
        }
        throw new RuntimeException("Failed to create book. Status: " + response.getStatusCode());
    }

    public Book updateBookAndReturn(int bookId, Book book, String token) {
        Response response = updateBook(bookId, book, token);
        if (response.getStatusCode() == 200) {
            return response.as(Book.class);
        }
        throw new RuntimeException("Failed to update book. Status: " + response.getStatusCode());
    }
}
