package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.ViewsLocators;
import org.automation.utils.AndroidActions;
import org.automation.utils.WaitHelper;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Views sub-menu screen.
 */
public class ViewsPage extends AndroidActions {

    private final WaitHelper waitHelper;

    /**
     * Constructs a ViewsPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public ViewsPage(AndroidDriver driver) {
        super(driver);
        this.waitHelper = new WaitHelper(driver);
    }

    /**
     * Checks if the Views sub-menu is loaded by verifying the Expandable Lists item is visible.
     *
     * @return true if the Expandable Lists element is displayed, false otherwise.
     */
    public boolean isExpandableListsDisplayed() {
        try {
            WebElement element = driver.findElement(AppiumBy.accessibilityId(ViewsLocators.EXPANDABLE_LISTS));
            return waitHelper.waitForVisibility(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
