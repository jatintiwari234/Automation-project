package com.jatin.saucedemo.tests;

import com.jatin.saucedemo.base.BaseTest;
import com.jatin.saucedemo.config.ConfigReader;
import com.jatin.saucedemo.pages.CartPage;
import com.jatin.saucedemo.pages.CheckoutPage;
import com.jatin.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {
    private CheckoutPage checkout;

    @BeforeMethod(alwaysRun = true)
    public void prepareCheckout() {
        InventoryPage inventory = loginPage.loginAs(
                ConfigReader.get("standardUsername"), ConfigReader.get("password"));
        CartPage cart = inventory.addProductToCart("add-to-cart-sauce-labs-backpack")
                .openCart();
        checkout = cart.proceedToCheckout();
    }

    @Test(groups = {"regression"})
    public void checkoutRequiresFirstName() {
        checkout.enterCustomerInformation("", "Tiwari", "313001")
                .continueToOverview();
        Assert.assertEquals(checkout.getErrorMessage(),
                "Error: First Name is required");
    }

    @Test(groups = {"smoke", "regression"})
    public void userCanCompletePurchase() {
        checkout.enterCustomerInformation("Jatin", "Tiwari", "313001")
                .continueToOverviewSuccessfully();
        Assert.assertTrue(checkout.getItemTotal().contains("29.99"));
        Assert.assertTrue(checkout.getTotal().contains("$"));
        checkout.finishOrder();
        Assert.assertEquals(checkout.getOrderConfirmation(), "Thank you for your order!");
        Assert.assertTrue(checkout.isBackHomeDisplayed());
    }

    @Test(groups = {"regression"})
    public void checkoutRequiresPostalCode() {
        checkout.enterCustomerInformation("Jatin", "Tiwari", "")
                .continueToOverview();
        Assert.assertEquals(checkout.getErrorMessage(),
                "Error: Postal Code is required");
    }
}
