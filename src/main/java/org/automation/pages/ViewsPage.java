package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.automation.locators.ViewsLocators;
import org.automation.base.AndroidActions;
import org.automation.utils.ScrollHelper;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Views sub-menu screen.
 */
public class ViewsPage extends AndroidActions {

    private final ScrollHelper scrollHelper;

    /**
     * Constructs a new ViewsPage instance.
     *
     * @param driver The AndroidDriver instance.
     */
    public ViewsPage(AndroidDriver driver) {
        super(driver);
        this.scrollHelper = new ScrollHelper(driver);
    }

    private WebElement expandableListsItem() {
        return driver.findElement(AppiumBy.accessibilityId(ViewsLocators.EXPANDABLE_LISTS));
    }

    private WebElement layoutsItem() {
        return driver.findElement(AppiumBy.accessibilityId(ViewsLocators.LAYOUTS));
    }

    private WebElement dragAndDropItem() {
        return driver.findElement(AppiumBy.accessibilityId(ViewsLocators.DRAG_AND_DROP));
    }

    private WebElement controlsItem() {
        return driver.findElement(AppiumBy.accessibilityId(ViewsLocators.CONTROLS));
    }

    private WebElement listsItem() {
        return driver.findElement(AppiumBy.accessibilityId(ViewsLocators.LISTS));
    }

    private WebElement progressBarItem() {
        return driver.findElement(AppiumBy.accessibilityId(ViewsLocators.PROGRESS_BAR));
    }

    /**
     * Checks if the Views sub-menu is loaded by verifying the Expandable Lists item is visible.
     *
     * @return true if the Expandable Lists element is displayed, false otherwise.
     */
    public boolean isExpandableListsDisplayed() {
        return isElementDisplayed(expandableListsItem());
    }

    /**
     * Opens the Layouts sub-menu by scrolling to and tapping Layouts.
     */
    public void openLayouts() {
        scrollHelper.scrollToText(ViewsLocators.LAYOUTS);
        layoutsItem().click();
    }

    /**
     * Opens the Drag and Drop screen by scrolling to and tapping Drag and Drop.
     */
    public void openDragAndDrop() {
        scrollHelper.scrollToText(ViewsLocators.DRAG_AND_DROP);
        dragAndDropItem().click();
    }

    /**
     * Opens the Expandable Lists sub-menu by tapping Expandable Lists.
     */
    public void openExpandableLists() {
        expandableListsItem().click();
    }

    /**
     * Opens the Controls sub-menu by scrolling to and tapping Controls.
     */
    public void openControls() {
        scrollHelper.scrollToText(ViewsLocators.CONTROLS);
        controlsItem().click();
    }

    /**
     * Opens the Lists sub-menu by scrolling down until Lists is visible, then tapping it.
     */
    public void openListsPage() {
        scrollHelper.scrollToText(ViewsLocators.LAYOUTS);
        listsItem().click();
    }

    /**
     * Opens the Progress Bar sub-menu by scrolling to and tapping Progress Bar.
     */
    public void openProgressBar() {
        scrollHelper.scrollToText(ViewsLocators.PROGRESS_BAR);
        progressBarItem().click();
    }
}
