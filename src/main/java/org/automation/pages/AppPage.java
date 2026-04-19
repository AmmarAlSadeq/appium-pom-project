package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.AppLocators;
import org.openqa.selenium.WebElement;

/**
 * Page object for the App sub-menu screen.
 */
public class AppPage {

    AndroidDriver driver;

    public AppPage(AndroidDriver driver) {
        this.driver = driver;
    }

    private WebElement alertDialogsItem() {
        return driver.findElement(AppiumBy.accessibilityId(AppLocators.ALERT_DIALOGS));
    }

    /**
     * Opens the Alert Dialogs screen by tapping Alert Dialogs.
     */
    public void openAlertDialogs() {
        alertDialogsItem().click();
    }
}
