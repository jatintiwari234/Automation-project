package com.jatin.saucedemo.pages;

import com.jatin.saucedemo.base.BasePage;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class CartPage extends BasePage {
    private final By cartList = By.className("cart_list");
    private final By cartItems = By.className("cart_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage waitUntilLoaded() {
        visible(cartList);
        return this;
    }

    public int getCartItemCount() {
        return findAll(cartItems).size();
    }

    public List<String> getCartProductNames() {
        return findAll(itemNames).stream()
                .map(element -> element.getText().trim())
                .collect(Collectors.toList());
    }

    public CartPage removeProduct(String productId) {
        By removeButton = By.id(productId.replace("add-to-cart", "remove"));
        click(removeButton);
        waitForInvisibility(removeButton);
        return this;
    }

    public InventoryPage continueShopping() {
        click(continueShoppingButton);
        waitForUrlContains("inventory.html");
        return new InventoryPage().waitUntilLoaded();
    }

    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        waitForUrlContains("checkout-step-one.html");
        return new CheckoutPage();
    }
}
