package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.ControlsLocators;
import org.automation.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Controls > Light Theme screen.
 */
public class ControlsPage extends AndroidActions {

    public ControlsPage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement lightThemeItem() {
        return driver.findElement(AppiumBy.accessibilityId(ControlsLocators.LIGHT_THEME));
    }

    private WebElement button() {
        return driver.findElement(By.id(ControlsLocators.BUTTON));
    }

    private WebElement checkbox() {
        return driver.findElement(AppiumBy.accessibilityId(ControlsLocators.CHECKBOX));
    }

    private WebElement radio1() {
        return driver.findElement(AppiumBy.id(ControlsLocators.RADIO_1));
    }

    private WebElement toggleButton() {
        return driver.findElement(AppiumBy.id(ControlsLocators.TOGGLE_BUTTON));
    }

    /**
     * Opens the Light Theme controls screen by tapping "1. Light Theme".
     */
    public void openLightTheme() {
        lightThemeItem().click();
    }

    /**
     * Checks if the Button is clickable.
     *
     * @return true if the button is enabled.
     */
    public boolean isButtonClickable() {
        return button().isEnabled();
    }

    /**
     * Toggles the checkbox.
     */
    public void toggleCheckbox() {
        checkbox().click();
    }

    /**
     * Gets the current checked state of the checkbox.
     *
     * @return true if checkbox is checked.
     */
    public boolean isCheckboxChecked() {
        return isChecked(checkbox());
    }

    /**
     * Selects radio button 1.
     */
    public void selectRadio1() {
        radio1().click();
    }

    /**
     * Checks if radio button 1 is selected.
     *
     * @return true if radio button 1 is selected.
     */
    public boolean isRadio1Selected() {
        return isChecked(radio1());
    }

    /**
     * Taps the toggle button.
     */
    public void tapToggleButton() {
        toggleButton().click();
    }

    /**
     * Gets the current state of the toggle button.
     *
     * @return true if toggle is ON.
     */
    public boolean isToggleButtonOn() {
        return isChecked(toggleButton());
    }
}
