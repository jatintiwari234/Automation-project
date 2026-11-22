package com.jatin.saucedemo.pages;

import com.jatin.saucedemo.base.BasePage;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By itemTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.className("complete-header");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutPage enterCustomerInformation(String first, String last, String postal) {
        type(firstName, first);
        type(lastName, last);
        type(postalCode, postal);
        return this;
    }

    /** Clicks Continue. Use {@link #getErrorMessage()} afterwards for validation cases. */
    public CheckoutPage continueToOverview() {
        click(continueButton);
        return this;
    }

    /** Clicks Continue and waits until the order overview page is shown. */
    public CheckoutPage continueToOverviewSuccessfully() {
        click(continueButton);
        waitForUrlContains("checkout-step-two.html");
        return this;
    }

    public String getErrorMessage() {
        return text(errorMessage);
    }

    public String getItemTotal() {
        return text(itemTotal);
    }

    public String getTax() {
        return text(tax);
    }

    public String getTotal() {
        return text(total);
    }

    public CheckoutPage finishOrder() {
        click(finishButton);
        waitForUrlContains("checkout-complete.html");
        return this;
    }

    public String getOrderConfirmation() {
        return text(completeHeader);
    }

    public boolean isBackHomeDisplayed() {
        return isDisplayed(backHomeButton);
    }
}
