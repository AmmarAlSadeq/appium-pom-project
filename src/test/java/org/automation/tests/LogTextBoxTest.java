package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.LogTextBoxPage;
import org.automation.pages.TextPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.automation.testUtils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * LogTextBox Test Suite.
 * Contains all test cases related to the LogTextBox screen.
 * Navigation: Home -> Text -> LogTextBox
 */
public class LogTextBoxTest extends AndroidBaseClass {

    private static final String DATA_FILE = "src/test/java/org/automation/testData/logtextbox_testdata.json";

    @Test(description = "[TC-007] Type text entries and verify log appends both strings",
            retryAnalyzer = RetryAnalyzer.class)
    public void testLogTextBox() {
        HomePage homePage = new HomePage(driver);
        TextPage textPage = new TextPage(driver);
        LogTextBoxPage logTextBoxPage = new LogTextBoxPage(driver);

        homePage.openTextPage();
        textPage.openLogTextBox();
        logTextBoxPage.clearInput();
        logTextBoxPage.typeText(TestDataReader.getData(DATA_FILE, "firstInput"));
        logTextBoxPage.tapAdd();
        Assert.assertEquals(logTextBoxPage.getLogText(),
                TestDataReader.getData(DATA_FILE, "firstExpectedLog"),
                "Log should contain first entry after Add");
        logTextBoxPage.typeText(TestDataReader.getData(DATA_FILE, "secondInput"));
        logTextBoxPage.tapAdd();
        Assert.assertEquals(logTextBoxPage.getLogText(),
                TestDataReader.getData(DATA_FILE, "secondExpectedLog"),
                "Log should contain both entries after second Add");
    }
}
