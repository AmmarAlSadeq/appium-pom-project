package org.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.automation.base.BasePage;
import org.automation.locators.LogTextBoxLocators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Page object for the LogTextBox screen.
 * The input field (id: text) serves as both the text input and the log display.
 */
public class LogTextBoxPage extends BasePage {

    /**
     * Constructs a LogTextBoxPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public LogTextBoxPage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement inputField() {
        return driver.findElement(By.id(LogTextBoxLocators.INPUT_FIELD));
    }

    private WebElement addButton() {
        return driver.findElement(By.id(LogTextBoxLocators.ADD_BUTTON));
    }

    /**
     * Clears the input field.
     */
    public void clearInput() {
        inputField().clear();
    }

    /**
     * Types the given text into the input field (appends to existing content).
     *
     * @param text the text to type
     */
    public void typeText(String text) {
        inputField().sendKeys(text);
    }

    /**
     * Taps the Add button to append the current input to the log.
     */
    public void tapAdd() {
        addButton().click();
    }

    /**
     * Gets the current log text from the input field element.
     *
     * @return the accumulated log text
     */
    public String getLogText() {
        return inputField().getText();
    }

}
