package com.jatin.saucedemo.tests;

import com.jatin.saucedemo.base.BaseTest;
import com.jatin.saucedemo.config.ConfigReader;
import com.jatin.saucedemo.dataproviders.LoginDataProvider;
import com.jatin.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    @Test(groups = {"smoke", "regression"})
    public void standardUserCanLogin() {
        InventoryPage inventory = loginPage.loginAs(
                ConfigReader.get("standardUsername"), ConfigReader.get("password"));
        Assert.assertEquals(inventory.getPageTitle(), "Products");
        Assert.assertEquals(inventory.getProductCount(), 6);
    }

    @Test(dataProvider = "invalidLoginData",
          dataProviderClass = LoginDataProvider.class,
          groups = {"regression"})
    public void invalidCredentialsShowExpectedError(
            String username, String password, String expectedMessage) {
        loginPage.enterUsername(username).enterPassword(password).submitLogin();
        Assert.assertEquals(loginPage.getErrorMessage(), expectedMessage);
    }

    @Test(groups = {"regression"})
    public void lockedOutUserCannotLogin() {
        loginPage.enterUsername(ConfigReader.get("lockedUsername"))
                .enterPassword(ConfigReader.get("password"))
                .submitLogin();
        Assert.assertEquals(loginPage.getErrorMessage(),
                "Epic sadface: Sorry, this user has been locked out.");
    }
}
