package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.base.BasePage;
import org.automation.locators.HomeLocators;
import org.openqa.selenium.WebElement;

/**
 * Page object for the ApiDemos Home screen.
 */
public class HomePage extends BasePage {

    /**
     * Constructs a new HomePage instance.
     *
     * @param driver The AndroidDriver instance.
     */
    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement viewsCategory() {
        return driver.findElement(AppiumBy.accessibilityId(HomeLocators.VIEWS));
    }

    private WebElement appCategory() {
        return driver.findElement(AppiumBy.accessibilityId(HomeLocators.APP));
    }

    private WebElement textCategory() {
        return driver.findElement(AppiumBy.accessibilityId(HomeLocators.TEXT));
    }

    /**
     * Checks if a specific category is displayed using accessibility ID.
     *
     * @param accessibilityId The accessibility ID of the category.
     * @return true if the category is visible, false otherwise.
     */
    public boolean isCategoryDisplayed(String accessibilityId) {
        return isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(accessibilityId)));
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
        viewsCategory().click();
    }

    /**
     * Opens the App page by tapping the App category.
     */
    public void openAppPage() {
        appCategory().click();
    }

    /**
     * Opens the Text page by tapping the Text category.
     */
    public void openTextPage() {
        textCategory().click();
    }
}
