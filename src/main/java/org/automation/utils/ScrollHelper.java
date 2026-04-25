package org.automation.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Reusable scroll utility with configurable direction, duration, and max swipes.
 * Uses UiScrollable for text-based scrolling and W3C Actions for gesture-based scrolling.
 */
public class ScrollHelper {

    private final AndroidDriver driver;

    /**
     * Constructs a ScrollHelper.
     *
     * @param driver The AndroidDriver instance.
     */
    public ScrollHelper(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Scrolls to find an element by its visible text using UiScrollable.
     *
     * @param text The text to scroll to.
     * @return true if element found, false otherwise.
     */
    public boolean scrollToText(String text) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))"
                            + ".scrollIntoView(new UiSelector().textContains(\"" + text + "\"))"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scrolls down using Appium mobile:scroll command.
     *
     * @return true if scroll succeeded.
     */
    public boolean scrollDown() {
        try {
            ((JavascriptExecutor) driver).executeScript("mobile: scroll",
                    java.util.Collections.singletonMap("direction", "down"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scrolls up using Appium mobile:scroll command.
     *
     * @return true if scroll succeeded.
     */
    public boolean scrollUp() {
        try {
            ((JavascriptExecutor) driver).executeScript("mobile: scroll",
                    java.util.Collections.singletonMap("direction", "up"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scrolls to the very bottom of a scrollable view using UiScrollable.
     *
     * @return true if scroll to end succeeded.
     */
    public boolean scrollToBottom() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollToEnd(20)"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scrolls to the very top of a scrollable view using UiScrollable.
     *
     * @return true if scroll to beginning succeeded.
     */
    public boolean scrollToTop() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollToBeginning(20)"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
