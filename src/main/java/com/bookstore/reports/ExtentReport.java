package com.bookstore.reports;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.bookstore.configs.BookstoreConfigReader;

import java.util.Objects;

import static com.bookstore.constants.BookstoreConstants.EXTENT_REPORT_PATH;

public final class ExtentReport {
    private ExtentReport(){}
    private static ExtentReports extentReports;
    public static void initReport(){
        if(Objects.isNull(extentReports)) {
            extentReports = new ExtentReports();
            ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(EXTENT_REPORT_PATH);
            extentSparkReporter.config().setReportName("Bookstore Api Automation Framework Report");
            extentSparkReporter.config().setDocumentTitle("Bookstore Api Report");
            extentSparkReporter.config().setTheme(Theme.DARK);
            extentReports.attachReporter(extentSparkReporter);
            extentReports.setSystemInfo("Environment", BookstoreConfigReader.config().environment().toUpperCase());
            extentReports.setSystemInfo("Automation Engineer","UMAJI BAGAL");
        }
    }

    public static void flushReport(){
        if(Objects.nonNull(extentReports)){
            extentReports.flush();
            ExtentManager.unload();
        }
    }
    public static void createTest(String testCaseName){
        ExtentManager.setExtentTest(extentReports.createTest(testCaseName));
    }
    public static void assignAuthor(String author){
        ExtentManager.getExtentTest().assignAuthor(author);
    }
    public static void assignCategory(String category){
        ExtentManager.getExtentTest().assignCategory(category);
    }
}
