package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.HorizontalScrollPage;
import org.automation.pages.LayoutsPage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.image.BufferedImage;

/**
 * TC-010: E2E Horizontal Swipe — Full 4-Screen Navigation Flow.
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

        Assert.assertTrue(homePage.areAllCategoriesDisplayed(),
                "All 11 categories should be displayed on home screen");

        homePage.openViewsPage();
        Assert.assertTrue(viewsPage.isExpandableListsDisplayed(),
                "Views sub-menu should be visible after tapping Views");

        viewsPage.openLayouts();
        layoutsPage.openHorizontalScrollView();

        Assert.assertTrue(horizontalScrollPage.isScrollViewDisplayed(),
                "HorizontalScrollView should be displayed");
        Assert.assertFalse(horizontalScrollPage.getContentText().isEmpty(),
                "HorizontalScrollView should have text content");

        BufferedImage beforeSwipe = horizontalScrollPage.captureScrollViewScreenshot();

        horizontalScrollPage.swipeLeft();

        BufferedImage afterSwipeLeft = horizontalScrollPage.captureScrollViewScreenshot();
        double diffAfterLeft = horizontalScrollPage.getImageDifferencePercent(beforeSwipe, afterSwipeLeft);
        Assert.assertTrue(diffAfterLeft > 0.03,
                "Screen should change after swiping left");

        horizontalScrollPage.swipeRight();

        BufferedImage afterSwipeRight = horizontalScrollPage.captureScrollViewScreenshot();
        double diffAfterRight = horizontalScrollPage.getImageDifferencePercent(beforeSwipe, afterSwipeRight);
        Assert.assertTrue(diffAfterRight < 0.06,
                "Screen should restore to original after swiping right");
    }
}
