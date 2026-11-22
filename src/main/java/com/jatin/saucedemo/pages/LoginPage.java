package com.jatin.saucedemo.pages;

import com.jatin.saucedemo.base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage open(String url) {
        log.info("Opening {}", url);
        driver.get(url);
        return waitUntilLoaded();
    }

    public LoginPage waitUntilLoaded() {
        visible(loginButton);
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public InventoryPage loginAs(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        click(loginButton);
        waitForUrlContains("inventory.html");
        return new InventoryPage().waitUntilLoaded();
    }

    public LoginPage submitLogin() {
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return text(errorMessage);
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(loginButton);
    }
}
