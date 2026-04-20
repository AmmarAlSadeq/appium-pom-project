package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.LogTextBoxPage;
import org.automation.pages.TextPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-007: LogTextBox — Type Text and Verify Log Appends.
 * Navigation: Home -> Text -> LogTextBox
 */
public class LogTextBoxTest extends AndroidBaseClass {

    @Test(description = "[TC-007] Type text entries and verify log appends both strings",
            retryAnalyzer = RetryAnalyzer.class)
    public void testLogTextBox() {
        HomePage homePage = new HomePage(driver);
        TextPage textPage = new TextPage(driver);
        LogTextBoxPage logTextBoxPage = new LogTextBoxPage(driver);

        homePage.openTextPage();
        textPage.openLogTextBox();

        logTextBoxPage.clearInput();
        logTextBoxPage.typeText("Appium POM Test ");
        logTextBoxPage.tapAdd();

        Assert.assertEquals(logTextBoxPage.getLogText(), "Appium POM Test This is a test\n");

        logTextBoxPage.typeText("Automation Engineer ");
        logTextBoxPage.tapAdd();

        Assert.assertEquals(logTextBoxPage.getLogText(), "Appium POM Test This is a test\nAutomation Engineer This is a test\n");
    }
}
