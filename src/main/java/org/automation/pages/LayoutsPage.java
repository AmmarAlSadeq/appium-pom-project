package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.base.AndroidActions;
import org.automation.locators.LayoutsLocators;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Layouts sub-menu screen.
 */
public class LayoutsPage extends AndroidActions {

    /**
     * Constructs a new LayoutsPage instance.
     *
     * @param driver The AndroidDriver instance.
     */
    public LayoutsPage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement scrollViewItem() {
        return driver.findElement(AppiumBy.accessibilityId(LayoutsLocators.SCROLLVIEW));
    }

    private WebElement horizontalScrollViewItem() {
        return driver.findElement(AppiumBy.accessibilityId(LayoutsLocators.HORIZONTAL_SCROLL_VIEW));
    }

    /**
     * Opens the ScrollView sub-menu by tapping ScrollView.
     */
    public void openScrollView() {
        scrollViewItem().click();
    }

    /**
     * Opens the HorizontalScrollView by tapping it.
     */
    public void openHorizontalScrollView() {
        horizontalScrollViewItem().click();
    }
}
