package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.ProgressBarLocators;
import org.automation.base.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Progress Bar > Incremental screen.
 */
public class ProgressBarPage extends AndroidActions {

    /**
     * Constructs a ProgressBarPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public ProgressBarPage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement incrementalItem() {
        return driver.findElement(AppiumBy.accessibilityId(ProgressBarLocators.INCREMENTAL));
    }

    private WebElement progressBar() {
        return driver.findElement(By.id(ProgressBarLocators.PROGRESS_BAR));
    }

    private WebElement increaseButton() {
        return driver.findElement(By.id(ProgressBarLocators.INCREASE_BUTTON));
    }

    private WebElement decreaseButton() {
        return driver.findElement(By.id(ProgressBarLocators.DECREASE_BUTTON));
    }

    private WebElement progressValueElement() {
        return driver.findElement(By.id(ProgressBarLocators.PROGRESS_BAR));
    }

    /**
     * Opens the Incremental progress bar screen by tapping "1. Incremental".
     */
    public void openIncremental() {
        incrementalItem().click();
    }

    /**
     * Gets the current progress value as a double.
     *
     * @return The current progress value (0.0-100.0).
     */
    public double getProgressValue() {
        return Double.parseDouble(progressValueElement().getText().trim());
    }

    /**
     * Taps the Increase button the specified number of times.
     *
     * @param times Number of times to tap Increase.
     * @return The progress value after all taps.
     */
    public double increaseProgress(int times) {
        for (int i = 0; i < times; i++) {
            increaseButton().click();
        }
        return getProgressValue();
    }

    /**
     * Taps the Decrease button the specified number of times.
     *
     * @param times Number of times to tap Decrease.
     * @return The progress value after all taps.
     */
    public double decreaseProgress(int times) {
        for (int i = 0; i < times; i++) {
            decreaseButton().click();
        }
        return getProgressValue();
    }
}
