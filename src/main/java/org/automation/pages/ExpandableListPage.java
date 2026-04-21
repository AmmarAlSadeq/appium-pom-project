package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.base.BasePage;
import org.automation.locators.ExpandableListLocators;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Expandable Lists > Custom Adapter screen.
 */
public class ExpandableListPage extends BasePage {

    /**
     * Constructs an ExpandableListPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public ExpandableListPage(AndroidDriver driver) {
        super(driver);
    }

    private WebElement customAdapterItem() {
        return driver.findElement(AppiumBy.accessibilityId(ExpandableListLocators.CUSTOM_ADAPTER));
    }

    private WebElement peopleNamesGroup() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + ExpandableListLocators.PEOPLE_NAMES + "\")"));
    }

    private WebElement dogNamesGroup() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + ExpandableListLocators.DOG_NAMES + "\")"));
    }

    private WebElement childArnold() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + ExpandableListLocators.CHILD_ARNOLD + "\")"));
    }

    private WebElement childBarry() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + ExpandableListLocators.CHILD_BARRY + "\")"));
    }

    private WebElement childChuck() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + ExpandableListLocators.CHILD_CHUCK + "\")"));
    }

    private WebElement childDavid() {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + ExpandableListLocators.CHILD_DAVID + "\")"));
    }

    /**
     * Opens the Custom Adapter expandable list by tapping "1. Custom Adapter".
     */
    public void openCustomAdapter() {
        customAdapterItem().click();
    }

    /**
     * Taps the "People Names" group header to expand or collapse it.
     */
    public void tapPeopleNamesGroup() {
        peopleNamesGroup().click();
    }

    /**
     * Taps the "Dog Names" group header to expand or collapse it.
     */
    public void tapDogNamesGroup() {
        dogNamesGroup().click();
    }

    /**
     * Checks if all children of People Names group are displayed.
     *
     * @return true if all four children are visible.
     */
    public boolean arePeopleNamesChildrenDisplayed() {
        try {
            return childArnold().isDisplayed()
                    && childBarry().isDisplayed()
                    && childChuck().isDisplayed()
                    && childDavid().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
