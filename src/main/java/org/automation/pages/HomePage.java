package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.HomeLocators;
import org.automation.utils.AndroidActions;
import org.automation.utils.WaitHelper;
import org.openqa.selenium.WebElement;

/**
 * Page object for the ApiDemos Home screen.
 */
public class HomePage extends AndroidActions {

    private final WaitHelper waitHelper;

    /**
     * Constructs a HomePage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public HomePage(AndroidDriver driver) {
        super(driver);
        this.waitHelper = new WaitHelper(driver);
    }

    /**
     * Checks if a specific category is displayed using accessibility ID.
     *
     * @param accessibilityId The accessibility ID of the category.
     * @return true if the category is visible, false otherwise.
     */
    public boolean isCategoryDisplayed(String accessibilityId) {
        try {
            WebElement element = driver.findElement(AppiumBy.accessibilityId(accessibilityId));
            return waitHelper.waitForVisibility(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifies all 11 expected categories are displayed.
     *
     * @return true if all categories are visible.
     */
    public boolean areAllCategoriesDisplayed() {
        return isCategoryDisplayed(HomeLocators.ACCESSIBILITY)
                && isCategoryDisplayed(HomeLocators.ANIMATION)
                && isCategoryDisplayed(HomeLocators.APP)
                && isCategoryDisplayed(HomeLocators.CONTENT)
                && isCategoryDisplayed(HomeLocators.GRAPHICS)
                && isCategoryDisplayed(HomeLocators.MEDIA)
                && isCategoryDisplayed(HomeLocators.NFC)
                && isCategoryDisplayed(HomeLocators.OS)
                && isCategoryDisplayed(HomeLocators.PREFERENCE)
                && isCategoryDisplayed(HomeLocators.TEXT)
                && isCategoryDisplayed(HomeLocators.VIEWS);
    }

    /**
     * Opens the Views page by tapping the Views category.
     */
    public void openViewsPage() {
        WebElement views = driver.findElement(AppiumBy.accessibilityId(HomeLocators.VIEWS));
        waitHelper.waitForClickable(views).click();
    }
}
