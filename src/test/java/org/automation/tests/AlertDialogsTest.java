package org.automation.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.automation.pages.AlertDialogsPage;
import org.automation.pages.AppPage;
import org.automation.pages.HomePage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Map;

/**
 * TC-006: Alert Dialogs — Trigger, Interact and Dismiss Three Dialog Types.
 * Navigation: Home -> App -> Alert Dialogs
 */
public class AlertDialogsTest extends AndroidBaseClass {

    @Test(description = "[TC-006] Trigger, interact and dismiss three dialog types",
            retryAnalyzer = RetryAnalyzer.class)
    public void testAlertDialogs() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> messages = mapper.readValue(
                new File("src/test/java/org/automation/testData/messages.json"), Map.class);

        Map<String, Object> alertDialogsMessages = (Map<String, Object>) messages.get("alertDialogs");
        String expectedListMessage = (String) alertDialogsMessages.get("listDialogSelection");

        HomePage homePage = new HomePage(driver);
        AppPage appPage = new AppPage(driver);
        AlertDialogsPage alertDialogsPage = new AlertDialogsPage(driver);

        homePage.openAppPage();
        appPage.openAlertDialogs();

        // OK Cancel dialog
        alertDialogsPage.tapOkCancelDialog();
        Assert.assertTrue(alertDialogsPage.isDialogDisplayed());
        alertDialogsPage.tapDialogOk();
        Assert.assertTrue(alertDialogsPage.isDialogDismissed());

        // List dialog
        alertDialogsPage.tapListDialog();
        Assert.assertTrue(alertDialogsPage.isDialogDisplayed());
        alertDialogsPage.selectCommandOne();
        Assert.assertEquals(alertDialogsPage.getDialogMessage(), expectedListMessage);
        alertDialogsPage.dismissDialog();
        Assert.assertTrue(alertDialogsPage.isDialogDismissed());

        // Single choice dialog
        alertDialogsPage.tapSingleChoiceDialog();
        Assert.assertTrue(alertDialogsPage.isDialogDisplayed());
        alertDialogsPage.selectSatelliteOption();
        alertDialogsPage.tapDialogOk();
        Assert.assertTrue(alertDialogsPage.isDialogDismissed());
    }
}
