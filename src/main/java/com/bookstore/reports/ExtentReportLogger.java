package com.bookstore.reports;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
    public static void warning(String message){
        ExtentManager.getExtentTest().warning(MarkupHelper.createLabel(message, ExtentColor.ORANGE));
    }
    public static void info(String message){
        ExtentManager.getExtentTest().info(message);
    }

    public static void logRequestInfo(RequestSpecification requestSpecification){
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        if(Objects.nonNull(ExtentManager.getExtentTest())) {
            info("Request Method : " + requestSpecification);
            info("Request Base Uri : " + queryableRequestSpecification.getBaseUri());
            info("Request Content-Type : " + queryableRequestSpecification.getContentType());
            info("Request Body : "+queryableRequestSpecification.getBody());
        }
    }


    public static void logResponseInfo(Response response){
        info("Response Status Code : "+response.statusCode());
        info("Response Headers : "+response.getHeaders());
        info("Response Content-Type : "+response.getContentType());
        info("Response Body : ");
        info(response.getBody().prettyPrint());
    }
}
