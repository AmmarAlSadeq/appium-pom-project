package org.automation.utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

/**
 * Reusable swipe utility using W3C PointerInput Actions API.
 * No deprecated TouchAction or MobileElement.swipe() calls.
 */
public class SwipeHelper {

    private final AndroidDriver driver;
    private static final int DEFAULT_DURATION_MS = 800;
    private static final double DEFAULT_OFFSET = 0.75;

    /**
     * Constructs a SwipeHelper.
     *
     * @param driver The AndroidDriver instance.
     */
    public SwipeHelper(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * Swipes left across the screen.
     *
     * @param durationMs Duration of the swipe in milliseconds.
     * @param offset     Percentage of screen width to swipe (0.0 to 1.0).
     * @throws org.openqa.selenium.WebDriverException if the swipe gesture fails to execute.
     */
    public void swipeLeft(int durationMs, double offset) {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        int startX = (int) (width * (1 - offset / 2));
        int endX = (int) (width * (offset / 2));
        int y = height / 2;

        performSwipe(startX, y, endX, y, durationMs);
    }

    /**
     * Swipes right across the screen.
     *
     * @param durationMs Duration of the swipe in milliseconds.
     * @param offset     Percentage of screen width to swipe (0.0 to 1.0).
     * @throws org.openqa.selenium.WebDriverException if the swipe gesture fails to execute.
     */
    public void swipeRight(int durationMs, double offset) {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        int startX = (int) (width * (offset / 2));
        int endX = (int) (width * (1 - offset / 2));
        int y = height / 2;

        performSwipe(startX, y, endX, y, durationMs);
    }

    /**
     * Swipes up on the screen.
     *
     * @param durationMs Duration of the swipe in milliseconds.
     * @param offset     Percentage of screen height to swipe (0.0 to 1.0).
     * @throws org.openqa.selenium.WebDriverException if the swipe gesture fails to execute.
     */
    public void swipeUp(int durationMs, double offset) {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        int startY = (int) (height * (1 - offset / 2));
        int endY = (int) (height * (offset / 2));
        int x = width / 2;

        performSwipe(x, startY, x, endY, durationMs);
    }

    /**
     * Swipes down on the screen.
     *
     * @param durationMs Duration of the swipe in milliseconds.
     * @param offset     Percentage of screen height to swipe (0.0 to 1.0).
     * @throws org.openqa.selenium.WebDriverException if the swipe gesture fails to execute.
     */
    public void swipeDown(int durationMs, double offset) {
        int height = driver.manage().window().getSize().getHeight();
        int width = driver.manage().window().getSize().getWidth();
        int startY = (int) (height * (offset / 2));
        int endY = (int) (height * (1 - offset / 2));
        int x = width / 2;

        performSwipe(x, startY, x, endY, durationMs);
    }

    /**
     * Performs a horizontal swipe at a specific Y coordinate.
     *
     * @param startX Starting X coordinate.
     * @param startY Y coordinate (constant for horizontal swipe).
     * @param endX   Ending X coordinate.
     * @throws org.openqa.selenium.WebDriverException if the swipe gesture fails to execute.
     */
    public void horizontalSwipe(int startX, int startY, int endX) {
        performSwipe(startX, startY, endX, startY, DEFAULT_DURATION_MS);
    }

    /**
     * Performs a vertical swipe at a specific X coordinate.
     *
     * @param startX X coordinate (constant for vertical swipe).
     * @param startY Starting Y coordinate.
     * @param endY   Ending Y coordinate.
     * @throws org.openqa.selenium.WebDriverException if the swipe gesture fails to execute.
     */
    public void verticalSwipe(int startX, int startY, int endY) {
        performSwipe(startX, startY, startX, endY, DEFAULT_DURATION_MS);
    }

    /**
     * Core swipe method using W3C PointerInput Actions.
     */
    private void performSwipe(int startX, int startY, int endX, int endY, int durationMs) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationMs), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }
}
