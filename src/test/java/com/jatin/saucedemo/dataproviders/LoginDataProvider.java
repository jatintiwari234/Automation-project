package com.jatin.saucedemo.dataproviders;

import org.testng.annotations.DataProvider;

public class LoginDataProvider {
    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {
        return new Object[][] {
                {"invalid_user", "secret_sauce",
                        "Epic sadface: Username and password do not match any user in this service"},
                {"standard_user", "wrong_password",
                        "Epic sadface: Username and password do not match any user in this service"},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"}
        };
    }
}
