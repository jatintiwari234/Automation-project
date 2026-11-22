package com.jatin.saucedemo.pages;

import com.jatin.saucedemo.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {
    private final By title = By.cssSelector(".title");
    private final By inventoryItems = By.cssSelector(".inventory_item");
    private final By itemNames = By.className("inventory_item_name");
    private final By itemPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");
    private final By cartLink = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public InventoryPage waitUntilLoaded() {
        visibleAll(inventoryItems);
        return this;
    }

    public String getPageTitle() {
        return text(title);
    }

    public int getProductCount() {
        return visibleAll(inventoryItems).size();
    }

    public List<String> getProductNames() {
        return visibleAll(itemNames).stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return visibleAll(itemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "").trim()))
                .collect(Collectors.toList());
    }

    public InventoryPage sortBy(String visibleText) {
        log.info("Sort by: {}", visibleText);
        WebElement dropdown = visible(sortDropdown);
        new Select(dropdown).selectByVisibleText(visibleText);
        // Wait until the dropdown really shows the chosen option and the list is rendered again
        wait.until(d -> new Select(visible(sortDropdown)).getFirstSelectedOption()
                .getText().equals(visibleText));
        visibleAll(inventoryItems);
        return this;
    }

    public InventoryPage addProductToCart(String productId) {
        click(By.id(productId));
        // Deterministic signal that the click was processed: button flips to "Remove"
        visible(By.id(productId.replace("add-to-cart", "remove")));
        return this;
    }

    public InventoryPage removeProductFromCart(String productId) {
        click(By.id(productId.replace("add-to-cart", "remove")));
        // Button flips back to "Add to cart"
        visible(By.id(productId));
        return this;
    }

    public int getCartCount() {
        if (findAll(cartBadge).isEmpty()) {
            return 0;
        }
        return Integer.parseInt(text(cartBadge));
    }

    /** Waits until the cart badge disappears (cart empty). */
    public InventoryPage waitForCartToBeEmpty() {
        waitForInvisibility(cartBadge);
        return this;
    }

    public CartPage openCart() {
        click(cartLink);
        waitForUrlContains("cart.html");
        return new CartPage().waitUntilLoaded();
    }

    public LoginPage logout() {
        click(menuButton);
        // Side menu slides in with an animation - wait until the link is clickable
        click(logoutLink);
        return new LoginPage().waitUntilLoaded();
    }
}
