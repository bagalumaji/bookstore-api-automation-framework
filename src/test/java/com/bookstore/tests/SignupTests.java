package com.bookstore.tests;

import com.bookstore.apis.signup.SignupApi;
import com.bookstore.dataproviders.TestDataProvider;
import com.bookstore.factory.UserCredentialsFactory;
import com.bookstore.pojo.UserCredentials;
import com.bookstore.utils.ValidationUtility;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignupTests {

    @Test
    public void createUserApiTest() {
        // Arrange
        UserCredentials signupPojo = UserCredentialsFactory.createSignupDataWithNewEmail();

        //Act
        Response response = SignupApi.signUpApi(signupPojo);

        //Assert
        SignupApi.validateSignApi(response);
    }
    @Test(groups = {"smoke", "auth"}, priority = 3, dataProvider = "validUsers",dataProviderClass = TestDataProvider.class)
    public void testSignup_ValidUser_Positive(UserCredentials validUser) {
        // logInfo("Testing signup with valid user data - positive scenario");

        Response response = SignupApi.signUpApi(validUser);

        // Assuming signup returns 201 for successful creation
        if (response.getStatusCode() == 201) {
            ValidationUtility.validateStatusCode(response, 201);
            //logPass("User signup successful");
        } else if (response.getStatusCode() == 409) {
            // User already exists - this is acceptable for test data
            // logInfo("User already exists - this is expected for test data");
            ValidationUtility.validateStatusCode(response, 409);
        } else {
            Assert.fail("Unexpected status code: " + response.getStatusCode());
        }
    }
}

