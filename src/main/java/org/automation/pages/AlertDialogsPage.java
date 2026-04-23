package org.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.automation.locators.AlertDialogsLocators;
import org.automation.base.AndroidActions;
import org.automation.utils.ScrollHelper;
import org.automation.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Page object for the Alert Dialogs screen.
 */
public class AlertDialogsPage extends AndroidActions {

    private final ScrollHelper scrollHelper;
    private final WaitHelper waitHelper;

    /**
     * Constructs an AlertDialogsPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public AlertDialogsPage(AndroidDriver driver) {
        super(driver);
        this.scrollHelper = new ScrollHelper(driver);
        this.waitHelper = new WaitHelper(driver);
    }

    private WebElement okCancelDialogButton() {
        return driver.findElement(AppiumBy.accessibilityId(AlertDialogsLocators.OK_CANCEL_DIALOG));
    }

    private WebElement listDialogButton() {
        return driver.findElement(AppiumBy.accessibilityId(AlertDialogsLocators.LIST_DIALOG));
    }

    private WebElement dialogOkButton() {
        return driver.findElement(By.id(AlertDialogsLocators.DIALOG_OK_BUTTON));
    }

    private WebElement dialogTitle() {
        return driver.findElement(By.id(AlertDialogsLocators.DIALOG_TITLE));
    }

    /**
     * Checks if a dialog is currently displayed.
     *
     * @return true if dialog is visible.
     */
    public boolean isDialogDisplayed() {
        try {
            return dialogTitle().isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the dialog has been dismissed using an explicit wait.
     * Uses By-locator to avoid implicit wait penalty when the element
     * is already gone — returns in ~500ms instead of ~15s.
     *
     * @return true if dialog is no longer displayed.
     */
    public boolean isDialogDismissed() {
        return waitHelper.waitUntilInvisibleByLocator(By.id(AlertDialogsLocators.DIALOG_TITLE));
    }

    /**
     * Taps the OK Cancel dialog trigger button.
     */
    public void tapOkCancelDialog() {
        okCancelDialogButton().click();
    }

    /**
     * Taps the OK button on the current dialog.
     */
    public void tapDialogOk() {
        dialogOkButton().click();
    }

    /**
     * Taps the List dialog trigger button.
     */
    public void tapListDialog() {
        listDialogButton().click();
    }

    /**
     * Selects "Command one" from the list dialog.
     */
    public void selectCommandOne() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + AlertDialogsLocators.COMMAND_ONE + "\")")).click();
    }

    /**
     * Scrolls down and taps the Single choice dialog trigger.
     */
    public void tapSingleChoiceDialog() {
        scrollHelper.scrollToText(AlertDialogsLocators.SINGLE_CHOICE_DIALOG);
        driver.findElement(AppiumBy.accessibilityId(AlertDialogsLocators.SINGLE_CHOICE_DIALOG)).click();
    }

    /**
     * Selects the "Satellite" radio option in the single choice dialog.
     */
    public void selectSatelliteOption() {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + AlertDialogsLocators.SATELLITE + "\")")).click();
    }

    /**
     * Dismisses the current dialog by pressing the Android back button.
     */
    public void dismissDialog() {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}
