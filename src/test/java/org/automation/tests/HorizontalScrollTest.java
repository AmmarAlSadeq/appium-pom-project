package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.HorizontalScrollPage;
import org.automation.pages.LayoutsPage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Horizontal Scroll Test Suite.
 * Contains all test cases related to the HorizontalScrollView screen.
 * Navigation: Home -> Views -> Layouts -> HorizontalScrollView
 */
public class HorizontalScrollTest extends AndroidBaseClass {

    @Test(description = "[TC-010] E2E horizontal swipe navigation flow with screenshot-based verification",
            retryAnalyzer = RetryAnalyzer.class)
    public void testHorizontalSwipe() throws Exception {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        LayoutsPage layoutsPage = new LayoutsPage(driver);
        HorizontalScrollPage horizontalScrollPage = new HorizontalScrollPage(driver);

        homePage.openViewsPage();
        viewsPage.openLayouts();
        layoutsPage.openHorizontalScrollView();
        horizontalScrollPage.captureReference();
        Assert.assertTrue(horizontalScrollPage.isContentChangedAfterSwipeLeft(),
                "Screen should change after swiping left");
        Assert.assertTrue(horizontalScrollPage.isContentRestoredAfterSwipeRight(),
                "Screen should restore to original after swiping right");
    }
}
