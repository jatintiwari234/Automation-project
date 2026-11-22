package com.jatin.saucedemo.base;

import com.jatin.saucedemo.config.ConfigReader;
import com.jatin.saucedemo.driver.DriverFactory;
import com.jatin.saucedemo.pages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initializeDriver();
        loginPage = new LoginPage().open(ConfigReader.get("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
