package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.ScrollViewLocators;
import org.automation.base.AndroidActions;
import org.automation.utils.ScrollHelper;
import org.openqa.selenium.WebElement;

/**
 * Page object for the ScrollView > 2. Long screen.
 */
public class ScrollViewPage extends AndroidActions {

    private final ScrollHelper scrollHelper;

    /**
     * Constructs a ScrollViewPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public ScrollViewPage(AndroidDriver driver) {
        super(driver);
        this.scrollHelper = new ScrollHelper(driver);
    }

    private WebElement longScrollViewItem() {
        return driver.findElement(AppiumBy.accessibilityId(ScrollViewLocators.LONG_SCROLLVIEW));
    }

    /**
     * Opens the long scrollable content screen by tapping "2. Long".
     */
    public void openLongScrollView() {
        longScrollViewItem().click();
    }

    /**
     * Gets the text of the first visible element in the scrollable list.
     *
     * @return The text of the first visible element.
     */
    public String getFirstElementText() {
        WebElement element = driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiSelector().className(\"android.widget.TextView\").instance(0)"));
        return element.getText();
    }

    /**
     * Scrolls to the very bottom of the scrollable content.
     *
     * @return true if scroll to bottom succeeded.
     */
    public boolean scrollToBottom() {
        return scrollHelper.scrollToBottom();
    }

    /**
     * Scrolls back to the very top of the scrollable content.
     *
     * @return true if scroll to top succeeded.
     */
    public boolean scrollToTop() {
        return scrollHelper.scrollToTop();
    }

    /**
     * Checks if an element with the given text is currently displayed.
     *
     * @param text The text to look for.
     * @return true if element with text is visible.
     */
    public boolean isElementWithTextDisplayed(String text) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"" + text + "\")"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
