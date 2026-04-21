package org.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import org.automation.base.BasePage;

/**
 * Utility class for performing Android-specific gesture actions.
 * Extends BasePage to inherit driver and waitHelper.
 */
public class AndroidActions extends BasePage {

    /**
     * Constructs AndroidActions with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public AndroidActions(AndroidDriver driver) {
        super(driver);
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
     * Performs a swipe gesture on the specified element in the given direction.
     *
     * @param element   The WebElement to swipe on.
     * @param direction The swipe direction ("up", "down", "left", "right").
     * @throws org.openqa.selenium.WebDriverException if the swipe gesture fails to execute.
     */
    public void swipeToElementAction(WebElement element, String direction) {
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "direction", direction,
                "percent", 0.75
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
