package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.base.BasePage;
import org.automation.locators.TextLocators;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Text sub-menu screen.
 */
public class TextPage extends BasePage {

    /**
     * Constructs a new TextPage instance.
     *
     * @param driver The AndroidDriver instance.
     */
    public TextPage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement logTextBoxItem() {
        return driver.findElement(AppiumBy.accessibilityId(TextLocators.LOG_TEXT_BOX));
    }

    /**
     * Opens the LogTextBox screen by tapping LogTextBox.
     */
    public void openLogTextBox() {
        logTextBoxItem().click();
    }
}
