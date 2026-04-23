package org.automation.utils;

import java.time.Duration;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Custom explicit wait utility wrapping WebDriverWait.
 * No Thread.sleep calls — all waits use configurable polling.
 */
public class WaitHelper {

    private final AndroidDriver driver;
    private final WebDriverWait wait;
    private final int timeoutSeconds;

    /**
     * Constructs a WaitHelper with timeout from config.properties.
     *
     * @param driver The AndroidDriver instance.
     */
    public WaitHelper(AndroidDriver driver) {
        this.driver = driver;
        this.timeoutSeconds = loadTimeout();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    private static int loadTimeout() {
        try {
            return Integer.parseInt(ConfigReader.get("defaultWaitTimeout"));
        } catch (Exception e) {
            return 15;
        }
    }

    /**
     * Waits for element to be visible on screen.
     *
     * @param element The WebElement to wait for.
     * @return The visible WebElement.
     * @throws TimeoutException if element is not visible within the timeout.
     */
    public WebElement waitForVisibility(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element not visible after " + timeoutSeconds + " seconds: " + e.getMessage(), e);
        }
    }

    /**
     * Waits for element to be clickable.
     *
     * @param element The WebElement to wait for.
     * @return The clickable WebElement.
     * @throws TimeoutException if element is not clickable within the timeout.
     */
    public WebElement waitForClickable(WebElement element) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element not clickable after " + timeoutSeconds + " seconds: " + e.getMessage(), e);
        }
    }

    /**
     * Waits for specific text to be present in an element.
     *
     * @param element The WebElement to check.
     * @param text    The text to wait for.
     * @return true if text is present, false otherwise.
     */
    public boolean waitForTextPresent(WebElement element, String text) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Waits for an element to become invisible.
     *
     * @param element The WebElement to wait to disappear.
     * @return true if element becomes invisible.
     */
    public boolean waitUntilInvisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Waits for an element located by the given locator to become invisible.
     * Uses By locator to avoid triggering implicit wait on findElement.
     *
     * @param locator The By locator of the element to wait to disappear.
     * @return true if element becomes invisible or is not present.
     */
    public boolean waitUntilInvisibleByLocator(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Waits for element to be visible using a By locator.
     *
     * @param locator The By locator to find the element.
     * @return The visible WebElement.
     * @throws TimeoutException if element is not visible within the timeout.
     */
    public WebElement waitForVisibilityByLocator(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException("Element not visible by locator after " + timeoutSeconds + " seconds: " + locator.toString(), e);
        }
    }
}
