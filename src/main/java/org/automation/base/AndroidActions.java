package org.automation.base;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import org.automation.utils.WaitHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

/**
 * Base class for all android page objects.
 * Provides shared driver, wait, element state, and Android gesture methods.
 */
public class AndroidActions {

    protected AndroidDriver driver;
    protected WaitHelper waitHelper;

    /**
     * Constructs AndroidActions with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public AndroidActions(AndroidDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
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

    /**
     * Performs a long press gesture on the specified element.
     *
     * @param element The WebElement to long press.
     * @throws org.openqa.selenium.WebDriverException if the long press gesture fails to execute.
     */
    public void longPressAction(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "duration", 2000
        ));
    }

    /**
     * Performs a drag-and-drop gesture on the specified element to the given coordinates.
     *
     * @param element           The WebElement to drag.
     * @param dropXCoordinates  The X coordinate to drop at.
     * @param dropYCoordinates  The Y coordinate to drop at.
     * @throws org.openqa.selenium.WebDriverException if the drag-and-drop gesture fails to execute.
     */
    public void dragAndDropAction(WebElement element, int dropXCoordinates, int dropYCoordinates) {
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "endX", dropXCoordinates,
                "endY", dropYCoordinates
        ));
    }
}
