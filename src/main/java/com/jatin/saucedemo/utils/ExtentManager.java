package com.jatin.saucedemo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentManager {
    private static ExtentReports extent;

    private ExtentManager() {}

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            try {
                Path reportDirectory = Path.of("target", "extent-reports");
                Files.createDirectories(reportDirectory);
                ExtentSparkReporter spark = new ExtentSparkReporter(
                        reportDirectory.resolve("index.html").toString());
                spark.config().setDocumentTitle("SauceDemo Automation Results");
                spark.config().setReportName("UI Regression Execution");
                extent = new ExtentReports();
                extent.attachReporter(spark);
                extent.setSystemInfo("Framework", "Selenium + TestNG + Maven");
                extent.setSystemInfo("Language", "Java 17");
                extent.setSystemInfo("Application", "SauceDemo");
            } catch (Exception e) {
                throw new IllegalStateException("Unable to initialize Extent report.", e);
            }
        }
        return extent;
    }
}
