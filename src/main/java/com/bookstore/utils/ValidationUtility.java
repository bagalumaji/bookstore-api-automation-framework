package com.bookstore.utils;

import com.bookstore.constants.LoginConstants;
import com.bookstore.pojo.Book;
import com.bookstore.pojo.LoginResponse;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class ValidationUtility {

    public static void validateStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch. Expected: " + expectedStatusCode + ", Actual: " + actualStatusCode);
    }

    public static void validateResponseTime(Response response, long maxResponseTime) {
        long responseTime = response.getTime();
        Assert.assertTrue(responseTime <= maxResponseTime,
                "Response time exceeded. Expected: <= " + maxResponseTime + "ms, Actual: " + responseTime + "ms");
    }

    public static void validateContentType(Response response, String expectedContentType) {
        String actualContentType = response.getContentType();
        Assert.assertTrue(actualContentType.contains(expectedContentType),
                "Content type mismatch. Expected to contain: " + expectedContentType + ", Actual: " + actualContentType);
    }

    public static void validateResponseNotEmpty(Response response) {
        String responseBody = response.getBody().asString();
        Assert.assertNotNull(responseBody, "Response body is null");
        Assert.assertFalse(responseBody.trim().isEmpty(), "Response body is empty");
    }


    public static void validateLoginResponse(LoginResponse loginResponse) {
        Assert.assertNotNull(loginResponse, "Login response is null");
        Assert.assertNotNull(loginResponse.getAccess_token(), "Token is null");
        Assert.assertFalse(loginResponse.getAccess_token().trim().isEmpty(), "Token is empty");
        Assert.assertEquals(loginResponse.getToken_type(), LoginConstants.TOKEN_TYPE, LoginConstants.LOGIN_WAS_NOT_SUCCESSFUL);
    }

    public static void validateBookResponse(Book book) {
        Assert.assertNotNull(book, "Book response is null");
        Assert.assertNotNull(book.getId(), "Book ID is null");
        Assert.assertNotNull(book.getName(), "Book name is null");
        Assert.assertNotNull(book.getAuthor(), "Book author is null");
        Assert.assertFalse(book.getName().trim().isEmpty(), "Book name is empty");
        Assert.assertFalse(book.getAuthor().trim().isEmpty(), "Book author is empty");
    }


    public static void validateErrorResponse(Response response, int expectedStatusCode, String expectedMessageContains) {
        validateStatusCode(response, expectedStatusCode);

        String actualMessage = response.jsonPath().getString("detail");
        Assert.assertNotNull(actualMessage, "Error message is null");

        Assert.assertEquals(actualMessage, expectedMessageContains,
                "Error message doesn't contain expected text. Expected to contain: '" + expectedMessageContains +
                        "', Actual: '" + actualMessage + "'");
    }


    public static void validateHealthResponse(Response response) {
        validateStatusCode(response, 200);
        validateResponseTime(response, 5000);
        validateContentType(response, "application/json");
        validateResponseNotEmpty(response);
    }

    public static void validateBookCreation(Book expectedBook, Book actualBook) {
        Assert.assertNotNull(actualBook, "Created book is null");
        Assert.assertEquals(actualBook.getName(), expectedBook.getName(), "Book name mismatch");
        Assert.assertEquals(actualBook.getAuthor(), expectedBook.getAuthor(), "Book author mismatch");
        Assert.assertEquals(actualBook.getPublishedYear(), expectedBook.getPublishedYear(), "Published year mismatch");
        Assert.assertEquals(actualBook.getBookSummary(), expectedBook.getBookSummary(), "Book summary mismatch");
    }

    public static void validateBookUpdate(Book expectedBook, Book actualBook) {
        validateBookCreation(expectedBook, actualBook);
        Assert.assertEquals(actualBook.getId(), expectedBook.getId(), "Book ID mismatch during update");
    }

    public static void validateSuccessResponse(Response response, boolean expectedSuccess) {
        Boolean actualSuccess = response.jsonPath().get("success");
        Assert.assertNotNull(actualSuccess, "Success field is null");
        Assert.assertEquals(actualSuccess, expectedSuccess,
                "Success field mismatch. Expected: " + expectedSuccess + ", Actual: " + actualSuccess);
    }

    public static void validateBooksList(List<Book> books) {
        Assert.assertNotNull(books, "Books list is null");
        for (Book book : books) {
            validateBookResponse(book);
        }
    }
    public static void validateJsonPath(Response response, String jsonPath, Object expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        Assert.assertEquals(actualValue, expectedValue,
                "JSON path value mismatch for '" + jsonPath + "'. Expected: " + expectedValue + ", Actual: " + actualValue);
    }

    public static void validateJsonPathExists(Response response, String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        Assert.assertNotNull(value, "JSON path '" + jsonPath + "' not found in response");
    }

    public static void validateHeaderExists(Response response, String headerName) {
        String headerValue = response.getHeader(headerName);
        Assert.assertNotNull(headerValue, "Header '" + headerName + "' not found in response");
    }

    public static void validateHeaderValue(Response response, String headerName, String expectedValue) {
        String actualValue = response.getHeader(headerName);
        Assert.assertEquals(actualValue, expectedValue,
                "Header value mismatch for '" + headerName + "'. Expected: " + expectedValue + ", Actual: " + actualValue);
    }
}
