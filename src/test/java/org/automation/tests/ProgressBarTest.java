package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.ProgressBarPage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-009: Progress Bar > Incremental — Increment and Decrement Progress.
 * Navigation: Home -> Views -> Progress Bar -> 1. Incremental
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

        Assert.assertTrue(progressBarPage.isProgressBarDisplayed(),
                "Progress bar should be displayed on incremental screen");

        // Increase by 10% five times
        double previousValue = progressBarPage.getProgressValue();
        for (int i = 0; i < 5; i++) {
            progressBarPage.tapIncrease();
            double currentValue = progressBarPage.getProgressValue();
            Assert.assertTrue(currentValue >= previousValue,
                    "Progress should increase or stay same after tapping Increase");
            Assert.assertTrue(currentValue <= 100,
                    "Progress should not exceed 100%");
            previousValue = currentValue;
        }

        // Decrease by 10% three times
        for (int i = 0; i < 3; i++) {
            progressBarPage.tapDecrease();
            double currentValue = progressBarPage.getProgressValue();
            Assert.assertTrue(currentValue <= previousValue,
                    "Progress should decrease or stay same after tapping Decrease");
            Assert.assertTrue(currentValue >= 0,
                    "Progress should not go below 0%");
            previousValue = currentValue;
        }
    }
}
