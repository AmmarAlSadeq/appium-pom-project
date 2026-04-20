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

        Assert.assertTrue(controlsPage.isButtonClickable());

        controlsPage.toggleCheckbox();
        Assert.assertTrue(controlsPage.isCheckboxChecked());

        controlsPage.toggleCheckbox();
        Assert.assertFalse(controlsPage.isCheckboxChecked());

        controlsPage.selectRadio1();
        Assert.assertTrue(controlsPage.isRadio1Selected());

        boolean initialToggleState = controlsPage.isToggleButtonOn();
        controlsPage.tapToggleButton();
        Assert.assertNotEquals(controlsPage.isToggleButtonOn(), initialToggleState);
    }
    // Note: SeekBar is not present on the Controls > Light Theme screen in ApiDemos v6.0.6.
    // The screen only contains: Button, Checkbox, RadioButton, and ToggleButton.
}
