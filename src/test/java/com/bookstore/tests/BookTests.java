package com.bookstore.tests;


import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.book.BookApi;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.constants.ApiConstants;
import com.bookstore.constants.BookConstants;
import com.bookstore.dataproviders.BookDataProvider;
import com.bookstore.dataproviders.UserDataProvider;
import com.bookstore.enums.ApiStatusCodes;
import com.bookstore.factory.BookFactory;
import com.bookstore.pojo.Book;
import com.bookstore.specs.ApiRequestResponseSpec;
import com.bookstore.specs.ApiRequestSpecs;
import com.bookstore.specs.ApiResponseSpecs;
import com.bookstore.token.TokenManager;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class BookTests {

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke","regression", "books"}, priority = 1,description = "Verify Get Al lBooks With Valid Token Test")
    public void verifyGetAllBooksWithValidTokenTest() {
        Response response = new BookApi().getAllBooks(TokenManager.getToken());
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);
        ValidationUtility.validateResponseTime(response, BookstoreConfigReader.config().requestTimeout());
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "regression", "books"}, priority = 2,description = "verify Get All Books Without Token Test")
    public void verifyGetAllBooksWithoutTokenTest() {
        Response response = new BookApi().getAllBooks("");

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }
    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "regression", "books"}, priority = 3,description = "verifyGetAllBooksWithInvalidTokenTest")
    public void verifyGetAllBooksWithInvalidTokenTest() {
        Response response = new BookApi().getAllBooks(BookstoreConfigReader.config().invalidToken());

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.INVALID_TOKEN_OR_EXPIRED_TOKEN);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "regression", "books"}, priority = 4, dataProvider = "validBooks", dataProviderClass = BookDataProvider.class,description = "verify Create Book Valid Data Test")
    public void verifyCreateBookValidDataTest(Book validBook) {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken()))
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Response response = new BookApi().createBook(validBook,apiReqRes);
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        Book createdBook = response.as(Book.class);
        BookApi.validateBookResponse(createdBook);
        BookApi.validateBookCreation(validBook, createdBook);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 5,description = "verify Create Book Without Token Test")
    public void verifyCreateBookWithoutTokenTest() {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec())
                .respSpec(ApiResponseSpecs.getForbiddenResponseSpec())
                .build();

        Book validBook = BookFactory.createRandomBook();
        Response response = new BookApi().createBook(validBook,apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "regression", "books"}, priority = 6, dataProvider = "updateBookData",
            dataProviderClass = UserDataProvider.class,description = "verify Update Book Valid Data Test")
    public void verifyUpdateBookValidDataTest(Book updateData) {

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken()))
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Book originalBook = BookFactory.createRandomBook();

        Book createdBook = new BookApi().createBookAndReturn(originalBook, apiReqRes);
        updateData.setId(createdBook.getId());

        apiReqRes.setReqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), updateData.getId()));

        Response response = new BookApi().updateBook(updateData,apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        Book updatedBook = response.as(Book.class);
        BookApi.validateBookUpdate(updateData, updatedBook);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 7,description = "verify Update Book Non Existent IdT est")
    public void verifyUpdateBookNonExistentIdTest() {
        Book updateData = BookFactory.createRandomBook();

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), BookstoreConfigReader.config().nonExistentId()))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();
        Response response = new BookApi().updateBook(updateData, apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_FOUND.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_FOUND.getCode(), BookConstants.BOOK_NOT_FOUND);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 8,description = "verify Update Book Without Token Test")
    public void verifyUpdateBookWithoutTokenTest() {
        Book updateData = BookFactory.createRandomBook();

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec(updateData.getId()))
                .respSpec(ApiResponseSpecs.getForbiddenResponseSpec())
                .build();

        Response response = new BookApi().updateBook(updateData, apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "books","regression"}, priority = 9,description = "verif yDelete Book ValidId est")
    public void verifyDeleteBookValidIdTest() {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken()))
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Book originalBook = BookFactory.createRandomBook();

        Book createdBook = new BookApi().createBookAndReturn(originalBook, apiReqRes);

        apiReqRes.setReqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), createdBook.getId()));

        Response response = new BookApi().deleteBook(apiReqRes);
        response.prettyPrint();

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        Response getResponse = new BookApi().getBookById(apiReqRes);
        ValidationUtility.validateStatusCode(getResponse, ApiStatusCodes.NOT_FOUND.getCode());
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 10,description = "verify Delete Book Non Existent Id Test")
    public void verifyDeleteBookNonExistentIdTest() {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), BookstoreConfigReader.config().nonExistentId()))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();

        Response response = new BookApi().deleteBook(apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_FOUND.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_FOUND.getCode(), BookConstants.BOOK_NOT_FOUND);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 11,description = "verify Delete Book Without Token Test")
    public void verifyDeleteBookWithoutTokenTest() {

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec(BookstoreConfigReader.config().bookPathParamId()))
                .respSpec(ApiResponseSpecs.getForbiddenResponseSpec())
                .build();

        Response response = new BookApi().deleteBook(apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "books"}, priority = 12,description = "verify Get Book ById Valid Id Test")
    public void verifyGetBookByIdValidIdTest() {

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken()))
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Book bookToGet = BookFactory.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(bookToGet, apiReqRes);

        apiReqRes.setReqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), createdBook.getId()));
        Response response = new BookApi().getBookById(apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());
        ValidationUtility.validateContentType(response, ApiConstants.APPLICATION_JSON);

        Book retrievedBook = response.as(Book.class);
        BookApi.validateBookResponse(retrievedBook);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId(), BookConstants.BOOK_ID_MISMATCH);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 13,description = "verify Get Book ById Non ExistentId Test")
    public void verifyGetBookByIdNonExistentIdTest() {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), BookstoreConfigReader.config().nonExistentId()))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();

        Response response = new BookApi().getBookById(apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_FOUND.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_FOUND.getCode(), BookConstants.BOOK_NOT_FOUND);

    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 14,description = "verify Get Book ById Without Token Test")
    public void verifyGetBookByIdWithoutTokenTest() {

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpec(BookstoreConfigReader.config().bookPathParamId()))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();

        Response response = new BookApi().getBookById(apiReqRes);

        ValidationUtility.validateStatusCode(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode());
        ValidationUtility.validateErrorResponse(response, ApiStatusCodes.NOT_AUTHENTICATED.getCode(), ApiConstants.NOT_AUTHENTICATED);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 15, description = "verify Create Multiple Books Test", dataProvider = "bookTestData",dataProviderClass = BookDataProvider.class)
    public void verifyCreateMultipleBooksTest(Book book) {
        Response response = new BookApi().createBook(book, TokenManager.getToken());
        ValidationUtility.validateStatusCode(response, ApiStatusCodes.OK.getCode());

        Book createdBook = response.as(Book.class);
        BookApi.validateBookResponse(createdBook);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"performance", "books"}, priority = 16,description = "Verify Books endpoint response time is within acceptable limits Test")
    public void verifyBooksEndpointResponseTimeTest() {
        Response response = new BookApi().getAllBooks(TokenManager.getToken());
        ValidationUtility.validateResponseTime(response, BookstoreConfigReader.config().requestTimeout());
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 17,description = "verify Books CRUD Complete Flow Test")
    public void verifyBooksCRUDCompleteFlowTest() {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken()))
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        // Create
        Book originalBook = BookFactory.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(originalBook, apiReqRes);
        apiReqRes.setReqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), createdBook.getId()));

        // Read
        Response getResponse = new BookApi().getBookById(apiReqRes);
        getResponse.prettyPrint();
        ValidationUtility.validateStatusCode(getResponse, ApiStatusCodes.OK.getCode());
        Book retrievedBook = getResponse.as(Book.class);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId());

        // Update
        Book updateData = BookFactory.createValidBook("Updated Title", "Updated Author", 2024, "Updated summary");
        updateData.setId(createdBook.getId());

        Book updatedBook = new BookApi().updateBookAndReturn(updateData, apiReqRes);
        Assert.assertEquals(updatedBook.getName(), "Updated Title");

        // Delete
        Response deleteResponse = new BookApi().deleteBook(apiReqRes);
        ValidationUtility.validateStatusCode(deleteResponse, ApiStatusCodes.OK.getCode());

        apiReqRes.setRespSpec(ApiResponseSpecs.getNotFoundResponseSpec());
        Response verifyResponse = new BookApi().getBookById(apiReqRes);
        ValidationUtility.validateStatusCode(verifyResponse, ApiStatusCodes.NOT_FOUND.getCode());
    }
}
