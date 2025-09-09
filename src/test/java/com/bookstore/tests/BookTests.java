package com.bookstore.tests;


import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.book.BookApi;
import com.bookstore.configs.BookstoreConfigReader;
import com.bookstore.dataproviders.UserDataProvider;
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
    @Test(groups = {"smoke"}, priority = 1,description = "testGetAllBooks_WithValidToken_Positive")
    public void testGetAllBooks_WithValidToken_Positive() {
        Response response = new BookApi().getAllBooks(TokenManager.getToken());
        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");
        ValidationUtility.validateResponseTime(response, 5000);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 2,description = "testGetAllBooks_WithoutToken_Negative")
    public void testGetAllBooks_WithoutToken_Negative() {
        Response response = new BookApi().getAllBooks("");

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Not authenticated");
    }
    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 3,description = "testGetAllBooks_WithInvalidToken_Negative")
    public void testGetAllBooks_WithInvalidToken_Negative() {
        Response response = new BookApi().getAllBooks(BookstoreConfigReader.config().invalidToken());

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Invalid token or expired token");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "books"}, priority = 4, dataProvider = "validBooks", dataProviderClass = UserDataProvider.class,description = "testCreateBook_ValidData_Positive")
    public void testCreateBook_ValidData_Positive(Book validBook) {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken()))
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Response response = new BookApi().createBook(validBook,apiReqRes);
        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        Book createdBook = response.as(Book.class);
        BookApi.validateBookResponse(createdBook);
        BookApi.validateBookCreation(validBook, createdBook);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 5,description = "testCreateBook_WithoutToken_Negative")
    public void testCreateBook_WithoutToken_Negative() {
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(""))
                .respSpec(ApiResponseSpecs.getForbiddenResponseSpec())
                .build();

        Book validBook = BookFactory.createRandomBook();
        Response response = new BookApi().createBook(validBook,apiReqRes);

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Not authenticated");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "books"}, priority = 6, dataProvider = "updateBookData",
            dataProviderClass = UserDataProvider.class,description = "testUpdateBook_ValidData_Positive")
    public void testUpdateBook_ValidData_Positive(Book updateData) {

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

        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        Book updatedBook = response.as(Book.class);
        BookApi.validateBookUpdate(updateData, updatedBook);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 7,description = "testUpdateBook_NonExistentId_Negative")
    public void testUpdateBook_NonExistentId_Negative() {
        Book updateData = BookFactory.createRandomBook();

        int nonExistentId = 999999;
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), nonExistentId))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();
        Response response = new BookApi().updateBook(updateData, apiReqRes);

        ValidationUtility.validateStatusCode(response, 404);
        ValidationUtility.validateErrorResponse(response, 404, "Book not found");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 8,description = "testUpdateBook_WithoutToken_Negative")
    public void testUpdateBook_WithoutToken_Negative() {
        Book updateData = BookFactory.createRandomBook();

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth("", updateData.getId()))
                .respSpec(ApiResponseSpecs.getForbiddenResponseSpec())
                .build();

        Response response = new BookApi().updateBook(updateData, apiReqRes);

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Not authenticated");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "books"}, priority = 9,description = "Verified book is no longer accessible after deletion")
    public void testDeleteBook_ValidId_Positive() {
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

        ValidationUtility.validateStatusCode(response, 200);
        Response getResponse = new BookApi().getBookById(apiReqRes);
        ValidationUtility.validateStatusCode(getResponse, 404);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 10,description = "testDeleteBook_NonExistentId_Negative")
    public void testDeleteBook_NonExistentId_Negative() {
        int nonExistentId = 999999;
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), nonExistentId))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();

        Response response = new BookApi().deleteBook(apiReqRes);

        ValidationUtility.validateStatusCode(response, 404);
        ValidationUtility.validateErrorResponse(response, 404, "Book not found");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 11,description = "testDeleteBook_WithoutToken_Negative")
    public void testDeleteBook_WithoutToken_Negative() {

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth("",1))
                .respSpec(ApiResponseSpecs.getForbiddenResponseSpec())
                .build();

        Response response = new BookApi().deleteBook(apiReqRes);

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Not authenticated");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"smoke", "books"}, priority = 12,description = "testGetBookById_ValidId_Positive")
    public void testGetBookById_ValidId_Positive() {

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken()))
                .respSpec(ApiResponseSpecs.getSuccessResponseSpec())
                .build();

        Book bookToGet = BookFactory.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(bookToGet, apiReqRes);

        apiReqRes.setReqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), createdBook.getId()));
        Response response = new BookApi().getBookById(apiReqRes);

        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        Book retrievedBook = response.as(Book.class);
        BookApi.validateBookResponse(retrievedBook);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId(), "Book ID mismatch");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 13,description = "verify Get Book ById Non ExistentId Test")
    public void testGetBookById_NonExistentId_Negative() {

        int nonExistentId = 999999;
        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth(TokenManager.getToken(), nonExistentId))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();

        Response response = new BookApi().getBookById(apiReqRes);

        ValidationUtility.validateStatusCode(response, 404);
        ValidationUtility.validateErrorResponse(response, 404, "Book not found");

    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 14,description = "Verify Correctly rejected book retrieval without authentication Test")
    public void testGetBookById_WithoutToken_Negative() {

        ApiRequestResponseSpec apiReqRes=  ApiRequestResponseSpec
                .builder()
                .reqSpec(ApiRequestSpecs.getRequestSpecWithAuth("", 1))
                .respSpec(ApiResponseSpecs.getNotFoundResponseSpec())
                .build();

        Response response = new BookApi().getBookById(apiReqRes);

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Not authenticated");
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 15, description = "Successfully created multiple books Test", dataProvider = "bookTestData")
    public void testCreateMultipleBooks_Positive(Book book) {
        Response response = new BookApi().createBook(book, TokenManager.getToken());
        ValidationUtility.validateStatusCode(response, 200);

        Book createdBook = response.as(Book.class);
        BookApi.validateBookResponse(createdBook);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"performance", "books"}, priority = 16,description = "Verify Books endpoint response time is within acceptable limits Test")
    public void testBooksEndpoint_ResponseTime() {
        Response response = new BookApi().getAllBooks(TokenManager.getToken());
        ValidationUtility.validateResponseTime(response, 5000);
    }

    @Bookstore(author = "Umaji",category = "Books")
    @Test(groups = {"regression", "books"}, priority = 17,description = "verify Complete CRUD flow executed successfully Test")
    public void testBooksCRUD_CompleteFlow() {
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
        ValidationUtility.validateStatusCode(getResponse, 200);
        Book retrievedBook = getResponse.as(Book.class);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId());

        // Update
        Book updateData = BookFactory.createValidBook(
                "Updated Title", "Updated Author", 2024, "Updated summary"
        );
        updateData.setId(createdBook.getId());

        Book updatedBook = new BookApi().updateBookAndReturn(updateData, apiReqRes);
        Assert.assertEquals(updatedBook.getName(), "Updated Title");

        // Delete
        Response deleteResponse = new BookApi().deleteBook(apiReqRes);
        ValidationUtility.validateStatusCode(deleteResponse, 200);

        apiReqRes.setRespSpec(ApiResponseSpecs.getNotFoundResponseSpec());
        Response verifyResponse = new BookApi().getBookById(apiReqRes);
        ValidationUtility.validateStatusCode(verifyResponse, 404);
    }
}
