package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.base.AndroidActions;
import org.automation.locators.HomeLocators;
import org.openqa.selenium.WebElement;

/**
 * Page object for the ApiDemos Home screen.
 */
public class HomePage extends AndroidActions {

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
     * Verifies all 11 expected categories are displayed.
     *
     * @return true if all categories are visible.
     */
    public boolean areAllCategoriesDisplayed() {
        return isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.ACCESSIBILITY)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.ANIMATION)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.APP)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.CONTENT)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.GRAPHICS)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.MEDIA)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.NFC)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.OS)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.PREFERENCE)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.TEXT)))
                && isElementDisplayed(driver.findElement(AppiumBy.accessibilityId(HomeLocators.VIEWS)));
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
