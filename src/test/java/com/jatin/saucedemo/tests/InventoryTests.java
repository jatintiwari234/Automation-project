package com.jatin.saucedemo.tests;

import com.jatin.saucedemo.base.BaseTest;
import com.jatin.saucedemo.config.ConfigReader;
import com.jatin.saucedemo.pages.InventoryPage;
import com.jatin.saucedemo.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class InventoryTests extends BaseTest {
    private InventoryPage inventory;

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeInventoryTest() {
        inventory = loginPage.loginAs(
                ConfigReader.get("standardUsername"), ConfigReader.get("password"));
    }

    @Test(groups = {"smoke", "regression"})
    public void inventoryDisplaysSixProducts() {
        Assert.assertEquals(inventory.getProductCount(), 6);
    }

    @Test(groups = {"regression"})
    public void productsCanBeSortedByPriceLowToHigh() {
        inventory.sortBy("Price (low to high)");
        List<Double> prices = inventory.getProductPrices();
        Assert.assertEquals(prices, prices.stream().sorted().toList());
    }

    @Test(groups = {"regression"})
    public void productsCanBeSortedByNameZToA() {
        inventory.sortBy("Name (Z to A)");
        List<String> names = inventory.getProductNames();
        Assert.assertEquals(names, names.stream().sorted(java.util.Comparator.reverseOrder()).toList());
    }

    @Test(groups = {"regression"})
    public void addProductUpdatesCartBadge() {
        inventory.addProductToCart("add-to-cart-sauce-labs-backpack");
        Assert.assertEquals(inventory.getCartCount(), 1);
    }

    @Test(groups = {"regression"})
    public void removeProductResetsCartBadge() {
        inventory.addProductToCart("add-to-cart-sauce-labs-backpack");
        inventory.removeProductFromCart("add-to-cart-sauce-labs-backpack")
                .waitForCartToBeEmpty();
        Assert.assertEquals(inventory.getCartCount(), 0);
    }

    @Test(groups = {"regression"})
    public void logoutReturnsUserToLoginPage() {
        LoginPage returnedLoginPage = inventory.logout();
        Assert.assertTrue(returnedLoginPage.isLoginButtonDisplayed());
    }
}
