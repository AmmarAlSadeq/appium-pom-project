package org.automation.tests;

import org.automation.pages.AlertDialogsPage;
import org.automation.pages.AppPage;
import org.automation.pages.HomePage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.automation.testUtils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Alert Dialogs Test Suite.
 * Contains all test cases related to the Alert Dialogs screen.
 * Navigation: Home -> App -> Alert Dialogs
 */
public class AlertDialogsTest extends AndroidBaseClass {

    private static final String DATA_FILE = "src/test/java/org/automation/testData/alertDialogsTestData.json";

    @Test(description = "[TC-006] Trigger, interact and dismiss three dialog types",
            retryAnalyzer = RetryAnalyzer.class)
    public void testAlertDialogs() {
        HomePage homePage = new HomePage(driver);
        AppPage appPage = new AppPage(driver);
        AlertDialogsPage alertDialogsPage = new AlertDialogsPage(driver);

        homePage.openAppPage();
        appPage.openAlertDialogs();
        alertDialogsPage.tapOkCancelDialog();
        Assert.assertTrue(alertDialogsPage.isDialogDisplayed(),
                "OK Cancel dialog should be displayed");
        alertDialogsPage.tapDialogOk();
        Assert.assertTrue(alertDialogsPage.isDialogDismissed(),
                "OK Cancel dialog should be dismissed after tapping OK");
        alertDialogsPage.tapListDialog();
        Assert.assertTrue(alertDialogsPage.isDialogDisplayed(),
                "List dialog should be displayed");
        alertDialogsPage.selectCommandOne();
        Assert.assertEquals(alertDialogsPage.getListDialogResult(),
                TestDataReader.getData(DATA_FILE, "listDialogSelection"),
                "List dialog result should confirm Command one was selected");
        alertDialogsPage.dismissDialog();
        Assert.assertTrue(alertDialogsPage.isDialogDismissed(),
                "List dialog should be dismissed after pressing back");
        alertDialogsPage.tapSingleChoiceDialog();
        alertDialogsPage.selectSatelliteOption();
        alertDialogsPage.tapDialogOk();
        Assert.assertTrue(alertDialogsPage.isDialogDismissed(),
                "Single choice dialog should be dismissed after selecting and tapping OK");
    }
}
