package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.ListsLocators;
import org.automation.base.AndroidActions;
import org.automation.utils.ScrollHelper;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Lists > ListAdapter screen.
 */
public class ListsPage extends AndroidActions {

    private final ScrollHelper scrollHelper;

    /**
     * Constructs a ListsPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public ListsPage(AndroidDriver driver) {
        super(driver);
        this.scrollHelper = new ScrollHelper(driver);
    }

    private WebElement listAdapterItem() {
        return driver.findElement(AppiumBy.accessibilityId(ListsLocators.LIST_ADAPTER));
    }

    /**
     * Opens the ListAdapter screen by scrolling to and tapping "4. ListAdapter".
     */
    public void openListAdapter() {
        scrollHelper.scrollToText(ListsLocators.LIST_ADAPTER);
        listAdapterItem().click();
    }

    /**
     * Scrolls the ListView until the target item ("King Lear") is found using UiScrollable.
     *
     * @return true if the target item is found.
     */
    public boolean scrollUntilTargetItem() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))"
                            + ".scrollIntoView(new UiSelector().text(\"" + ListsLocators.TARGET_ITEM + "\"))"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
