package com.bookstore.tests;


import com.bookstore.apis.book.BookApi;
import com.bookstore.dataproviders.TestDataProvider;
import com.bookstore.pojo.Book;
import com.bookstore.token.TokenManager;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.bookstore.listeners.BookstoreListener.class)
public class BookTests {

    @Test(groups = {"smoke", "books"}, priority = 1,description = "testGetAllBooks_WithValidToken_Positive")
    public void testGetAllBooks_WithValidToken_Positive() {
        Response response = new BookApi().getAllBooks(TokenManager.getToken());
        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");
        ValidationUtility.validateResponseTime(response, 5000);
    }

    @Test(groups = {"regression", "books"}, priority = 2,description = "testGetAllBooks_WithoutToken_Negative")
    public void testGetAllBooks_WithoutToken_Negative() {
        Response response = new BookApi().getAllBooks("");

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Not authenticated");
    }

    @Test(groups = {"regression", "books"}, priority = 3,description = "testGetAllBooks_WithInvalidToken_Negative")
    public void testGetAllBooks_WithInvalidToken_Negative() {
        Response response = new BookApi().getAllBooks("invalidToken");

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Invalid token or expired token");
    }

    @Test(groups = {"smoke", "books"}, priority = 4, dataProvider = "validBooks", dataProviderClass = TestDataProvider.class,description = "testCreateBook_ValidData_Positive")
    public void testCreateBook_ValidData_Positive(Book validBook) {
        Response response = new BookApi().createBook(validBook, TokenManager.getToken());
        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        Book createdBook = response.as(Book.class);
        ValidationUtility.validateBookResponse(createdBook);
        ValidationUtility.validateBookCreation(validBook, createdBook);
    }

    @Test(groups = {"regression", "books"}, priority = 5, dataProvider = "invalidBooks",
            dataProviderClass = TestDataProvider.class,description = "testCreateBook_InvalidData_Negative")
    public void testCreateBook_InvalidData_Negative(Book invalidBook) {
        Response response = new BookApi().createBook(invalidBook, TokenManager.getToken());

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Invalid");
    }

    @Test(groups = {"regression", "books"}, priority = 6,description = "testCreateBook_WithoutToken_Negative")
    public void testCreateBook_WithoutToken_Negative() {
        Book validBook = TestDataProvider.createRandomBook();
        Response response = new BookApi().createBook(validBook, "");

        ValidationUtility.validateStatusCode(response, 403);
        ValidationUtility.validateErrorResponse(response, 403, "Not authenticated");
    }

    @Test(groups = {"smoke", "books"}, priority = 7, dataProvider = "updateBookData",
            dataProviderClass = TestDataProvider.class,description = "testUpdateBook_ValidData_Positive")
    public void testUpdateBook_ValidData_Positive(Book updateData) {
        Book originalBook = TestDataProvider.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(originalBook, TokenManager.getToken());

        updateData.setPublished_year(2025);
        Response response = new BookApi().updateBook(createdBook.getId(), updateData, TokenManager.getToken());
response.prettyPrint();
        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        Book updatedBook = response.as(Book.class);
        ValidationUtility.validateBookUpdate(updateData, updatedBook);

    }

    @Test(groups = {"regression", "books"}, priority = 8,description = "testUpdateBook_NonExistentId_Negative")
    public void testUpdateBook_NonExistentId_Negative() {

        Book updateData = TestDataProvider.createRandomBook();
        int nonExistentId = 999999;

        Response response = new BookApi().updateBook(nonExistentId, updateData, TokenManager.getToken());

        ValidationUtility.validateStatusCode(response, 404);
        ValidationUtility.validateErrorResponse(response, 404, "not found");
    }

    @Test(groups = {"regression", "books"}, priority = 9,description = "testUpdateBook_WithoutToken_Negative")
    public void testUpdateBook_WithoutToken_Negative() {

        Book updateData = TestDataProvider.createRandomBook();
        Response response = new BookApi().updateBook(1, updateData, "");

        ValidationUtility.validateStatusCode(response, 401);
        ValidationUtility.validateErrorResponse(response, 401, "Unauthorized");
    }

    @Test(groups = {"smoke", "books"}, priority = 10,description = "testDeleteBook_ValidId_Positive")
    public void testDeleteBook_ValidId_Positive() {
        Book bookToDelete = TestDataProvider.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(bookToDelete, "validToken");

        Response response = new BookApi().deleteBook(createdBook.getId(), "validToken");

        ValidationUtility.validateStatusCode(response, 204);
        //logPass("Book deleted successfully");

        // Verify book is deleted
        Response getResponse = new BookApi().getBookById(createdBook.getId(), "validToken");
        ValidationUtility.validateStatusCode(getResponse, 404);
        //logPass("Verified book is no longer accessible after deletion");
    }

    @Test(groups = {"regression", "books"}, priority = 11,description = "testDeleteBook_NonExistentId_Negative")
    public void testDeleteBook_NonExistentId_Negative() {
        int nonExistentId = 999999;
        Response response = new BookApi().deleteBook(nonExistentId, "validToken");

        ValidationUtility.validateStatusCode(response, 404);
        ValidationUtility.validateErrorResponse(response, 404, "not found");
    }

    @Test(groups = {"regression", "books"}, priority = 12,description = "testDeleteBook_WithoutToken_Negative")
    public void testDeleteBook_WithoutToken_Negative() {

        Response response = new BookApi().deleteBook(1, "");

        ValidationUtility.validateStatusCode(response, 401);
        ValidationUtility.validateErrorResponse(response, 401, "Unauthorized");
    }

    @Test(groups = {"smoke", "books"}, priority = 13,description = "testGetBookById_ValidId_Positive")
    public void testGetBookById_ValidId_Positive() {
        Book bookToGet = TestDataProvider.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(bookToGet, "validToken");

        Response response = new BookApi().getBookById(createdBook.getId(), "validToken");

        ValidationUtility.validateStatusCode(response, 200);
        ValidationUtility.validateContentType(response, "application/json");

        Book retrievedBook = response.as(Book.class);
        ValidationUtility.validateBookResponse(retrievedBook);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId(), "Book ID mismatch");
    }

    @Test(groups = {"regression", "books"}, priority = 14,description = "verify Get Book ById Non ExistentId Test")
    public void testGetBookById_NonExistentId_Negative() {

        int nonExistentId = 999999;
        Response response = new BookApi().getBookById(nonExistentId, "validToken");

        ValidationUtility.validateStatusCode(response, 404);
        ValidationUtility.validateErrorResponse(response, 404, "not found");

    }

    @Test(groups = {"regression", "books"}, priority = 15,description = "Verify Correctly rejected book retrieval without authentication Test")
    public void testGetBookById_WithoutToken_Negative() {

        Response response = new BookApi().getBookById(1, "");

        ValidationUtility.validateStatusCode(response, 401);
        ValidationUtility.validateErrorResponse(response, 401, "Unauthorized");
    }

    @Test(groups = {"regression", "books"}, priority = 16,description = "Successfully created multiple bookserify Successfully created multiple books Test")
    public void testCreateMultipleBooks_Positive() {
        for (int i = 1; i <= 3; i++) {
            Book book = TestDataProvider.createValidBook(
                    "Test Book " + i,
                    "Test Author " + i,
                    2020 + i,
                    "Test summary " + i
            );

            Response response = new BookApi().createBook(book, "validToken");
            ValidationUtility.validateStatusCode(response, 201);

            Book createdBook = response.as(Book.class);
            ValidationUtility.validateBookResponse(createdBook);

        }

    }

    @Test(groups = {"performance", "books"}, priority = 17,description = "Verify Books endpoint response time is within acceptable limits Test")
    public void testBooksEndpoint_ResponseTime() {
        Response response = new BookApi().getAllBooks("validToken");
        ValidationUtility.validateResponseTime(response, 5000);
    }

    @Test(groups = {"regression", "books"}, priority = 18,description = "verify Complete CRUD flow executed successfully Test")
    public void testBooksCRUD_CompleteFlow() {
        //logInfo("Testing complete CRUD flow for books");

        // Create
        Book originalBook = TestDataProvider.createRandomBook();
        Book createdBook = new BookApi().createBookAndReturn(originalBook, "validToken");
        //logInfo("1. Book created with ID: " + createdBook.getId());

        // Read
        Response getResponse = new BookApi().getBookById(createdBook.getId(), "validToken");
        ValidationUtility.validateStatusCode(getResponse, 200);
        Book retrievedBook = getResponse.as(Book.class);
        Assert.assertEquals(retrievedBook.getId(), createdBook.getId());
        //logInfo("2. Book retrieved successfully");

        // Update
        Book updateData = TestDataProvider.createValidBook(
                "Updated Title", "Updated Author", 2024, "Updated summary"
        );
        updateData.setId(createdBook.getId());
        Book updatedBook = new BookApi().updateBookAndReturn(createdBook.getId(), updateData, "validToken");
        Assert.assertEquals(updatedBook.getName(), "Updated Title");
        //logInfo("3. Book updated successfully");

        // Delete
        Response deleteResponse = new BookApi().deleteBook(createdBook.getId(), "validToken");
        ValidationUtility.validateStatusCode(deleteResponse, 204);
        //logInfo("4. Book deleted successfully");

        // Verify deletion
        Response verifyResponse = new BookApi().getBookById(createdBook.getId(), "validToken");
        ValidationUtility.validateStatusCode(verifyResponse, 404);
        //logInfo("5. Verified book deletion");

        //logPass("Complete CRUD flow executed successfully");
    }
}
