package com.jatin.saucedemo.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.jatin.saucedemo.utils.ExtentManager;
import com.jatin.saucedemo.utils.ScreenshotUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class TestListener implements ITestListener {
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        TEST.set(ExtentManager.getInstance().createTest(displayName(result)));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        currentTest(result).pass("Test passed");
        TEST.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = currentTest(result);
        test.fail(result.getThrowable());
        // Runs before @AfterMethod, so the browser is still open here
        String screenshot = ScreenshotUtils.capture(result.getMethod().getMethodName());
        if (screenshot != null) {
            try {
                test.fail("Failure screenshot",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshot).build());
            } catch (Exception e) {
                test.warning("Screenshot was saved but could not be attached: " + e.getMessage());
            }
        }
        TEST.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // A failed @BeforeMethod skips the test without calling onTestStart
        currentTest(result).skip(result.getThrowable());
        TEST.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.getInstance().flush();
    }

    private ExtentTest currentTest(ITestResult result) {
        ExtentTest test = TEST.get();
        if (test == null) {
            test = ExtentManager.getInstance().createTest(displayName(result));
            TEST.set(test);
        }
        return test;
    }

    private String displayName(ITestResult result) {
        String name = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        return params == null || params.length == 0 ? name : name + Arrays.toString(params);
    }
}
