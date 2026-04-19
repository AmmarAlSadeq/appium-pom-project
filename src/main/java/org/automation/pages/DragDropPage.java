package org.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.DragDropLocators;
import org.automation.utils.AndroidActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Drag and Drop screen.
 */
public class DragDropPage extends AndroidActions {

    public DragDropPage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement dot1() {
        return driver.findElement(By.id(DragDropLocators.DOT_1));
    }

    private WebElement dot2() {
        return driver.findElement(By.id(DragDropLocators.DOT_2));
    }

    private WebElement resultText() {
        return driver.findElement(By.id(DragDropLocators.RESULT_TEXT));
    }

    /**
     * Performs drag and drop from dot 1 to dot 2.
     */
    public void dragDot1ToDot2() {
        WebElement source = dot1();
        WebElement target = dot2();
        int dropX = target.getLocation().getX() + target.getSize().getWidth() / 2;
        int dropY = target.getLocation().getY() + target.getSize().getHeight() / 2;
        longPressAction(source);
        dragAndDropAction(source, dropX, dropY);
    }

    /**
     * Gets the text displayed in the result EditText after drag.
     *
     * @return The result text.
     */
    public String getResultText() {
        return resultText().getText();
    }
}
