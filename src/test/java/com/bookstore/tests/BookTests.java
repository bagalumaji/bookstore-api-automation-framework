package com.bookstore.tests;


import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.book.BookApi;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.constants.ApiConstants;
import com.bookstore.constants.BookConstants;
import com.bookstore.dataproviders.BookDataProvider;
import com.bookstore.enums.ApiStatusCodes;
import com.bookstore.factory.BookFactory;
import com.bookstore.pojo.Book;
import com.bookstore.specs.ApiResponseSpecs;
import com.bookstore.token.TokenManager;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.bookstore.constants.AnnotationConstants.BOOKS;
import static com.bookstore.constants.AnnotationConstants.UMAJI;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class BookTests {

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"smoke","regression", "books"}, priority = 1,description = "Verify Get Al lBooks With Valid Token Test")
    public void verifyGetAllBooksWithValidTokenTest() {
        Response response = new BookApi().getAllBooks(TokenManager.getToken());
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);
        ValidationUtility.validateResponseTime(response, BookstoreConfigReader.config().requestTimeout());
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "regression", "books"}, priority = 2,description = "verify Get All Books Without Token Test")
    public void verifyGetAllBooksWithoutTokenTest() {
        Response response = new BookApi().getAllBooks();

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }
    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "regression", "books"}, priority = 3,description = "verifyGetAllBooksWithInvalidTokenTest")
    public void verifyGetAllBooksWithInvalidTokenTest() {
        Response response = new BookApi().getAllBooks(BookstoreConfigReader.config().invalidToken());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.INVALID_TOKEN_OR_EXPIRED_TOKEN);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"smoke", "regression", "books"}, priority = 4, dataProvider = "validBooks", dataProviderClass = BookDataProvider.class,description = "verify Create Book Valid Data Test")
    public void verifyCreateBookValidDataTest(Book validBook) {
        Response response = new BookApi().createBook(TokenManager.getToken(),validBook,ApiResponseSpecs.getSuccessResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        Book createdBook = response.as(Book.class);
        BookApi.validateBookResponse(createdBook);
        BookApi.validateBookCreation(validBook, createdBook);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 5,description = "verify Create Book Without Token Test")
    public void verifyCreateBookWithoutTokenTest() {
        Book validBook = BookFactory.createRandomBook();
        Response response = new BookApi().createBook(validBook,ApiResponseSpecs.getForbiddenResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"smoke", "regression", "books"}, priority = 6, dataProvider = "updateBookData", dataProviderClass = BookDataProvider.class,description = "verify Update Book Valid Data Test")
    public void verifyUpdateBookValidDataTest(Book updateData) {

        Book originalBook = BookFactory.createRandomBook();

        BookApi bookApi = new BookApi();

        Response response = bookApi.createBook(TokenManager.getToken(),originalBook, ApiResponseSpecs.getSuccessResponseSpec());
        Book createdBook =  bookApi.getBook(response);
        updateData.setId(createdBook.getId());

        response = new BookApi().updateBook(TokenManager.getToken(),updateData,ApiResponseSpecs.getSuccessResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        Book updatedBook = response.as(Book.class);
        BookApi.validateBookUpdate(updateData, updatedBook);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 7,description = "verify Update Book Non Existent IdT est")
    public void verifyUpdateBookNonExistentIdTest() {
        Book updateData = BookFactory.createRandomBook();

        Response response = new BookApi().updateBook(TokenManager.getToken(),updateData, ApiResponseSpecs.getNotFoundResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_FOUND.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_FOUND.getCode(), BookConstants.BOOK_NOT_FOUND);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 8,description = "verify Update Book Without Token Test")
    public void verifyUpdateBookWithoutTokenTest() {
        Book updateData = BookFactory.createRandomBook();

        Response response = new BookApi().updateBook(updateData, ApiResponseSpecs.getForbiddenResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"smoke", "books","regression"}, priority = 9,description = "verif yDelete Book ValidId est")
    public void verifyDeleteBookValidIdTest() {
        Book originalBook = BookFactory.createRandomBook();

        BookApi bookApi = new BookApi();

        Response response = bookApi.createBook(TokenManager.getToken(),originalBook, ApiResponseSpecs.getSuccessResponseSpec());
        Book createdBook = bookApi.getBook(response);

        response = new BookApi().deleteBook(TokenManager.getToken(),createdBook.getId(),ApiResponseSpecs.getSuccessResponseSpec());
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());

        Response getResponse = new BookApi().getBookById(TokenManager.getToken(),createdBook.getId(),ApiResponseSpecs.getNotFoundResponseSpec());
        ValidationUtility.validateStatusCode(getResponse, ApiStatusCodes.NOT_FOUND.getCode());
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 10,description = "verify Delete Book Non Existent Id Test")
    public void verifyDeleteBookNonExistentIdTest() {
        Response response = new BookApi().deleteBook(TokenManager.getToken(),BookstoreConfigReader.config().nonExistentId(),ApiResponseSpecs.getNotFoundResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_FOUND.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_FOUND.getCode(), BookConstants.BOOK_NOT_FOUND);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 11,description = "verify Delete Book Without Token Test")
    public void verifyDeleteBookWithoutTokenTest() {
        Response response = new BookApi().deleteBook(BookstoreConfigReader.config().existingId(), ApiResponseSpecs.getForbiddenResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"smoke", "books"}, priority = 12,description = "verify Get Book ById Valid Id Test")
    public void verifyGetBookByIdValidIdTest() {
        Book bookToGet = BookFactory.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(TokenManager.getToken(),bookToGet, ApiResponseSpecs.getSuccessResponseSpec());

        Response response = new BookApi().getBookById(TokenManager.getToken(), createdBook.getId(),ApiResponseSpecs.getSuccessResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        Book retrievedBook = response.as(Book.class);
        BookApi.validateBookResponse(retrievedBook);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId(), BookConstants.BOOK_ID_MISMATCH);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 13,description = "verify Get Book ById Non ExistentId Test")
    public void verifyGetBookByIdNonExistentIdTest() {

        Response response = new BookApi().getBookById(TokenManager.getToken(), BookstoreConfigReader.config().nonExistentId(),ApiResponseSpecs.getNotFoundResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_FOUND.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_FOUND.getCode(), BookConstants.BOOK_NOT_FOUND);

    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 14,description = "verify Get Book ById Without Token Test")
    public void verifyGetBookByIdWithoutTokenTest() {
        Response response = new BookApi().getBookById(BookstoreConfigReader.config().existingId(), ApiResponseSpecs.getForbiddenResponseSpec());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 15, description = "verify Create Multiple Books Test", dataProvider = "bookTestData",dataProviderClass = BookDataProvider.class)
    public void verifyCreateMultipleBooksTest(Book book) {
        Response response = new BookApi().createBook(TokenManager.getToken(),book, ApiResponseSpecs.getSuccessResponseSpec());
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());

        Book createdBook = response.as(Book.class);
        BookApi.validateBookResponse(createdBook);
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"performance", "books"}, priority = 16,description = "Verify Books endpoint response time is within acceptable limits Test")
    public void verifyBooksEndpointResponseTimeTest() {
        Response response = new BookApi().getAllBooks(TokenManager.getToken());
        ValidationUtility.validateResponseTime(response, BookstoreConfigReader.config().requestTimeout());
    }

    @Bookstore(author = UMAJI,category = BOOKS)
    @Test(groups = {"regression", "books"}, priority = 17,description = "verify Books CRUD Complete Flow Test")
    public void verifyBooksCRUDCompleteFlowTest() {
        // Create
        Book originalBook = BookFactory.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(TokenManager.getToken(),originalBook, ApiResponseSpecs.getSuccessResponseSpec());

        // Read
        Response getResponse = new BookApi().getBookById(TokenManager.getToken(),createdBook.getId(),ApiResponseSpecs.getSuccessResponseSpec());
        getResponse.prettyPrint();
        ValidationUtility.validateStatusCode(getResponse, ApiStatusCodes.OK.getCode());
        Book retrievedBook = getResponse.as(Book.class);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId());

        // Update
        Book updateData = BookFactory.createValidBook("Updated Title", "Updated Author", 2024, "Updated summary");
        updateData.setId(createdBook.getId());

        Book updatedBook = new BookApi().updateBookAndReturn(TokenManager.getToken(),updateData, ApiResponseSpecs.getSuccessResponseSpec());
        Assert.assertEquals(updatedBook.getName(), "Updated Title");

        // Delete
        Response deleteResponse = new BookApi().deleteBook(TokenManager.getToken(),updatedBook.getId(),ApiResponseSpecs.getSuccessResponseSpec());
        ValidationUtility.validateStatusCode(deleteResponse, ApiStatusCodes.OK.getCode());

        Response verifyResponse = new BookApi().getBookById(TokenManager.getToken(),updatedBook.getId(),ApiResponseSpecs.getNotFoundResponseSpec());
        ValidationUtility.validateStatusCode(verifyResponse, ApiStatusCodes.NOT_FOUND.getCode());
    }
}