package com.bookstore.listeners;

import com.bookstore.annotation.Bookstore;
import com.bookstore.apis.signup.SignupApi;
import com.bookstore.reports.ExtentManager;
import com.bookstore.reports.ExtentReport;
import com.bookstore.reports.ExtentReportLogger;
import com.bookstore.token.Token;
import com.bookstore.token.TokenManager;
import com.bookstore.user.User;
import com.bookstore.user.UserManager;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Objects;

public class BookstoreListener implements ITestListener, ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        ExtentReport.initReport();
        User.createUser();
        if(Objects.isNull(TokenManager.getToken())) {
            ExtentReport.createTest("SignupTest");
            SignupApi.signUp(UserManager.getUserCredentials());
            Token.createToken(UserManager.getUserCredentials());
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        ExtentReport.flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        User.createUser();
        if(Objects.isNull(TokenManager.getToken())) {
            ExtentReport.createTest("SignupTest");
            SignupApi.signUp(UserManager.getUserCredentials());
            Token.createToken(UserManager.getUserCredentials());
        }
        ExtentReport.createTest(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description());

        ExtentReport.assignAuthor(result.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(Bookstore.class).author());
        ExtentReport.assignCategory(result.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(Bookstore.class).category());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportLogger.pass(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description()+ " is passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentReportLogger.info(result.getName() + " is failed");
        ExtentReportLogger.fail(result.getThrowable().getMessage() );
        ExtentReportLogger.info(Arrays.toString(result.getThrowable().getStackTrace()));
        ExtentReportLogger.fail(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description()+" is failed");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportLogger.skip(result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).description()+" is skipped");
    }

}