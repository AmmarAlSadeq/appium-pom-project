package org.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;

/**
 * Utility class for performing Android-specific actions using Appium.
 * Provides gesture methods: long press, swipe, drag-and-drop.
 */
public class AndroidActions extends AppiumUtils {
    protected AndroidDriver driver;

    /**
     * Constructs AndroidActions with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public AndroidActions(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Performs a long press gesture on the specified element.
     *
     * @param element The WebElement to long press.
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
     */
    public void dragAndDropAction(WebElement element, int dropXCoordinates, int dropYCoordinates) {
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture", ImmutableMap.of(
                "elementId", ((RemoteWebElement) element).getId(),
                "endX", dropXCoordinates,
                "endY", dropYCoordinates
        ));
    }
}
