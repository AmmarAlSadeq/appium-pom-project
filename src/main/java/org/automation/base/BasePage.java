package org.automation.base;

import io.appium.java_client.android.AndroidDriver;
import org.automation.utils.WaitHelper;
import org.openqa.selenium.WebElement;

/**
 * Base page providing shared driver and wait logic for all page objects.
 * Every page extends this directly or through AndroidActions.
 */
public class BasePage {

    protected AndroidDriver driver;
    protected WaitHelper waitHelper;

    /**
     * Initializes the driver and WaitHelper.
     *
     * @param driver The AndroidDriver instance.
     */
    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    /**
     * Waits for the element to be visible, then returns it.
     *
     * @param element The WebElement to wait for.
     * @return The visible WebElement.
     */
    protected WebElement waitForElement(WebElement element) {
        return waitHelper.waitForVisibility(element);
    }

    /**
     * Checks if an element is currently displayed.
     *
     * @param element The WebElement to check.
     * @return true if the element is visible.
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if an element is NOT displayed (dismissed or hidden).
     *
     * @param element The WebElement to check.
     * @return true if the element is not visible.
     */
    protected boolean isElementNotDisplayed(WebElement element) {
        try {
            return !element.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Gets the checked state of a checkbox, radio button, or toggle.
     *
     * @param element The WebElement to check.
     * @return true if the element is checked/ON.
     */
    protected boolean isChecked(WebElement element) {
        return Boolean.parseBoolean(element.getAttribute("checked"));
    }

    /**
     * Safely gets the text of an element, returning empty string on failure.
     *
     * @param element The WebElement to get text from.
     * @return The element text, or empty string if not found.
     */
    protected String getElementText(WebElement element) {
        try {
            return element.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
