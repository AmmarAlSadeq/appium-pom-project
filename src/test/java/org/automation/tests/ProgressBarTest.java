package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.ProgressBarPage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Progress Bar Test Suite.
 * Contains all test cases related to the Progress Bar screen.
 * Navigation: Home -> Views -> Progress Bar
 */
public class ProgressBarTest extends AndroidBaseClass {

    @Test(description = "[TC-009] Increment and decrement progress bar and verify value changes",
            retryAnalyzer = RetryAnalyzer.class)
    public void testProgressBarIncremental() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);

        homePage.openViewsPage();
        viewsPage.openProgressBar();
        progressBarPage.openIncremental();
        double initialValue = progressBarPage.getProgressValue();
        double valueAfterIncrease = progressBarPage.increaseProgress(5);
        Assert.assertEquals(valueAfterIncrease, initialValue + 5,
                "Progress should be initial + 5 after tapping Increase 5 times");
        double valueAfterDecrease = progressBarPage.decreaseProgress(3);
        Assert.assertEquals(valueAfterDecrease, valueAfterIncrease - 3,
                "Progress should be valueAfterIncrease - 3 after tapping Decrease 3 times");
    }
}
