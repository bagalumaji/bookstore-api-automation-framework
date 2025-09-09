package com.bookstore.apis.book;

import com.bookstore.client.ApiClient;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.constants.ApiConstants;
import com.bookstore.pojo.Book;
import com.bookstore.reports.ExtentReportLogger;
import com.bookstore.specs.ApiRequestSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;

public class BookApi {
    public Response getAllBooks(String token) {
        ExtentReportLogger.info("Requesting Method : GET");
        ExtentReportLogger.info("Get All Books with Token : ");
        return ApiClient.getWithAuth(BookstoreConfigReader.config().booksEndPoint(), token);
    }

    public Response getAllBooks() {
        ExtentReportLogger.info("Requesting Method : GET");
        ExtentReportLogger.info("Get All Books with Token ");
        return ApiClient.get(BookstoreConfigReader.config().booksEndPoint());
    }

    public static void validateBookCreation(Book expectedBook, Book actualBook) {
        ExtentReportLogger.info("Verifying Book Details");
        ExtentReportLogger.info("Actual Book : "+actualBook);
        ExtentReportLogger.info("Expected Book : "+expectedBook);
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
        ExtentReportLogger.info("Verifying Book Details : "+book);
        Assert.assertNotNull(book, "Book response is null");
        Assert.assertNotNull(book.getName(), "Book name is null");
        Assert.assertNotNull(book.getAuthor(), "Book author is null");
        Assert.assertFalse(book.getName().trim().isEmpty(), "Book name is empty");
        Assert.assertFalse(book.getAuthor().trim().isEmpty(), "Book author is empty");
    }

    public Book createBookAndReturn(String token, Book book, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : POST");
        ExtentReportLogger.info("Create Book And Return Book for : "+book);

        RequestSpecification reqSpec = ApiRequestSpecs.getRequestSpec(token, BookstoreConfigReader.config().booksEndPoint()).body(book);
        Response res = ApiClient.post(reqSpec, resSpec);
        return getBook(res);
    }

    public Response createBook(Book book, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : POST");
        ExtentReportLogger.info("Create Book And Return Book for : "+book);
        RequestSpecification reqSpec = ApiRequestSpecs.getRequestSpec().basePath(BookstoreConfigReader.config().booksEndPoint()).body(book);
        return ApiClient.post(reqSpec, resSpec);
    }

    public Book getBook(Response response) {
        Book book=response.as(Book.class);
        ExtentReportLogger.info("Creating Book : "+book);
        return book;
    }

    public Response updateBook(String token, Book book, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : PUT");
        ExtentReportLogger.info("Update Book And Return Book for : "+book);
        RequestSpecification reqSpec = ApiRequestSpecs.getRequestSpec(token, BookstoreConfigReader.config().booksEndPointWithParamId(), book.getId()).body(book);
        return ApiClient.put(reqSpec, resSpec);
    }

    public Book updateBookAndReturn(String token,Book book,  ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : PUT");
        ExtentReportLogger.info("Create Book And Return Book for : "+book);
        Response response = updateBook(token,book, resSpec);
        return getBook(response);
    }
    public Response deleteBook(String token, int id, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : DELETE");
        ExtentReportLogger.info("Create Book And Return Book for id: "+id);
        RequestSpecification reqSpec = ApiRequestSpecs.getRequestSpec(token, BookstoreConfigReader.config().booksEndPointWithParamId(), id);
        return ApiClient.delete(reqSpec, resSpec);
    }

    public Response deleteBook(int id, ResponseSpecification resSpec) {
        RequestSpecification reqSpec = ApiRequestSpecs
                .getRequestSpec()
                .basePath(BookstoreConfigReader.config().booksEndPointWithParamId())
                .pathParam(ApiConstants.ID, id);
        return ApiClient.delete(reqSpec, resSpec);
    }

    public Response createBook(String token, Book book, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : POST");
        ExtentReportLogger.info("Create Book And Return Book for : "+book);
        RequestSpecification reqSpec = ApiRequestSpecs.getRequestSpec(token, BookstoreConfigReader.config().booksEndPoint()).body(book);
        return ApiClient.post(reqSpec, resSpec);
    }

    public Response updateBook(Book book, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : PUT");
        ExtentReportLogger.info("update Book And Return Book for : "+book);
        RequestSpecification reqSpec = ApiRequestSpecs
                .getRequestSpec()
                .basePath(BookstoreConfigReader.config().booksEndPointWithParamId())
                .pathParam(ApiConstants.ID, book.getId())
                .body(book);
        return ApiClient.put(reqSpec, resSpec);
    }

    public Response getBookById(String token, int id, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : GET");
        ExtentReportLogger.info("GET Book And Return Book for id: "+id);
        RequestSpecification requestSpecification = ApiRequestSpecs.getRequestSpec(token, BookstoreConfigReader.config().booksEndPointWithParamId(), id);
        return ApiClient.get(requestSpecification, resSpec);
    }

    public Response getBookById(int id, ResponseSpecification resSpec) {
        ExtentReportLogger.info("Requesting Method : GET");
        ExtentReportLogger.info("GET Book And Return Book for id: "+id);
        RequestSpecification requestSpecification = ApiRequestSpecs
                .getRequestSpec()
                .basePath(BookstoreConfigReader.config().booksEndPointWithParamId())
                .pathParam(ApiConstants.ID,id);
        return ApiClient.get(requestSpecification, resSpec);
    }
}