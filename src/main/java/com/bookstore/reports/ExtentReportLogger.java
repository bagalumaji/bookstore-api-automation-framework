package com.bookstore.reports;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.response.Response;

public final class ExtentReportLogger {
    private ExtentReportLogger(){}

    public static void pass(String message){
        ExtentManager.getExtentTest().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }
    public static void fail(String message){
        ExtentManager.getExtentTest().fail(MarkupHelper.createLabel(message, ExtentColor.RED));
    }
    public static void skip(String message){
        ExtentManager.getExtentTest().skip(MarkupHelper.createLabel(message, ExtentColor.YELLOW));
    }
    public static void info(String message){
        ExtentManager.getExtentTest().info(message);
    }

    public static void logResponseInfo(Response response){
        info("Response Status Code : "+response.statusCode());
        info("Response Headers : "+response.getHeaders());
        info("Response Content-Type : "+response.getContentType());
        info("Response Body : "+response.getBody().toString());
    }
}
