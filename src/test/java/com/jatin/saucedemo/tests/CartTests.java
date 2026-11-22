package com.jatin.saucedemo.tests;

import com.jatin.saucedemo.base.BaseTest;
import com.jatin.saucedemo.config.ConfigReader;
import com.jatin.saucedemo.pages.CartPage;
import com.jatin.saucedemo.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTests extends BaseTest {
    private InventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeCartTest() {
        inventory = loginPage.loginAs(
                ConfigReader.get("standardUsername"), ConfigReader.get("password"));
    }

    @Test(groups = {"smoke", "regression"})
    public void addedProductAppearsInCart() {
        CartPage cart = inventory.addProductToCart("add-to-cart-sauce-labs-backpack")
                .openCart();
        Assert.assertEquals(cart.getCartItemCount(), 1);
        Assert.assertTrue(cart.getCartProductNames().contains("Sauce Labs Backpack"));
    }

    @Test(groups = {"regression"})
    public void removedProductDisappearsFromCart() {
        CartPage cart = inventory.addProductToCart("add-to-cart-sauce-labs-backpack")
                .openCart();
        cart.removeProduct("add-to-cart-sauce-labs-backpack");
        Assert.assertEquals(cart.getCartItemCount(), 0);
    }

    @Test(groups = {"regression"})
    public void cartCanContainMultipleProducts() {
        CartPage cart = inventory
                .addProductToCart("add-to-cart-sauce-labs-backpack")
                .addProductToCart("add-to-cart-sauce-labs-bike-light")
                .openCart();
        Assert.assertEquals(cart.getCartItemCount(), 2);
    }
}
