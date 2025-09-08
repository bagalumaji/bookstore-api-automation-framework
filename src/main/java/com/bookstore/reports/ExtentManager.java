package com.bookstore.reports;

import com.aventstack.extentreports.ExtentTest;

import java.util.Objects;

public final class ExtentManager {
    private ExtentManager(){}


    private static final ThreadLocal<ExtentTest> EXTENT_TEST_THREAD_LOCAL = new ThreadLocal<>();

    public static ExtentTest getExtentTest(){
        return EXTENT_TEST_THREAD_LOCAL.get();
    }

    static void setExtentTest(ExtentTest extentTest){
        if(Objects.nonNull(extentTest)){
            EXTENT_TEST_THREAD_LOCAL.set(extentTest);
        }
    }
    public static void unload(){
        EXTENT_TEST_THREAD_LOCAL.remove();
    }
}