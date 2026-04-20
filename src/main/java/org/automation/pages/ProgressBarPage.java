package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.ProgressBarLocators;
import org.automation.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Progress Bar > Incremental screen.
 */
public class ProgressBarPage extends AndroidActions {

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
     * Taps the Increase by 10% button.
     */
    public void tapIncrease() {
        increaseButton().click();
    }

    /**
     * Taps the Decrease by 10% button.
     */
    public void tapDecrease() {
        decreaseButton().click();
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
     * Checks if the progress bar is displayed.
     *
     * @return true if the progress bar is visible.
     */
    public boolean isProgressBarDisplayed() {
        return isElementDisplayed(progressBar());
    }
}
