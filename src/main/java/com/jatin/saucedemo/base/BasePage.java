package com.jatin.saucedemo.base;

import com.jatin.saucedemo.config.ConfigReader;
import com.jatin.saucedemo.driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Parent of every page object. All explicit waits live here so that page classes
 * and tests never need Thread.sleep() or implicit waits.
 */
public abstract class BasePage {
    protected final Logger log = LogManager.getLogger(getClass());
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver,
                Duration.ofSeconds(ConfigReader.getInt("explicitWait")));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        log.info("Click: {}", locator);
        clickable(locator).click();
    }

    protected void type(By locator, String value) {
        log.info("Type into: {}", locator);
        WebElement element = visible(locator);
        element.clear();
        if (value != null && !value.isEmpty()) {
            element.sendKeys(value);
        }
    }

    protected String text(By locator) {
        return visible(locator).getText().trim();
    }

    /** Waits for at least one matching element to be visible and returns all matches. */
    protected List<WebElement> visibleAll(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void waitForInvisibility(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected void waitForUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    /** Returns false (instead of throwing) when the element does not become visible in time. */
    protected boolean isDisplayed(By locator) {
        try {
            return visible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Non-waiting lookup. Use only where an empty result is a valid outcome (e.g. empty cart). */
    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }
}
