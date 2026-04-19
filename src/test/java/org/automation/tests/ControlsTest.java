package org.automation.tests;

import org.automation.pages.ControlsPage;
import org.automation.pages.HomePage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-005: Controls > Light Theme — Interact with All Input Controls.
 * Navigation: Home -> Views -> Controls -> 1. Light Theme
 */
public class ControlsTest extends AndroidBaseClass {

    @Test(description = "[TC-005] Interact with all input controls in Light Theme",
            retryAnalyzer = RetryAnalyzer.class)
    public void testControlsLightTheme() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        ControlsPage controlsPage = new ControlsPage(driver);

        homePage.openViewsPage();
        viewsPage.openControls();
        controlsPage.openLightTheme();

        Assert.assertTrue(controlsPage.isButtonClickable(),
                "Button should be clickable");

        controlsPage.toggleCheckbox();
        Assert.assertTrue(controlsPage.isCheckboxChecked(),
                "Checkbox should be checked after toggle ON");

        controlsPage.toggleCheckbox();
        Assert.assertFalse(controlsPage.isCheckboxChecked(),
                "Checkbox should be unchecked after toggle OFF");

        controlsPage.selectRadio1();
        Assert.assertTrue(controlsPage.isRadio1Selected(),
                "Radio button 1 should be selected");

        boolean initialToggleState = controlsPage.isToggleButtonOn();
        controlsPage.tapToggleButton();
        Assert.assertNotEquals(controlsPage.isToggleButtonOn(), initialToggleState,
                "Toggle button state should flip after tap");
    }
}
