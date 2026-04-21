package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.LayoutsPage;
import org.automation.pages.ScrollViewPage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-002: ScrollView — Scroll to Bottom and Back.
 * Navigation: Home -> Views -> Layouts -> ScrollView -> 2. Long
 */
public class ScrollViewTest extends AndroidBaseClass {

    @Test(description = "[TC-002] Scroll to bottom and back in long ScrollView",
            retryAnalyzer = RetryAnalyzer.class)
    public void testScrollToBottomAndBack() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        LayoutsPage layoutsPage = new LayoutsPage(driver);
        ScrollViewPage scrollViewPage = new ScrollViewPage(driver);

        homePage.openViewsPage();
        viewsPage.openLayouts();
        layoutsPage.openScrollView();
        scrollViewPage.openLongScrollView();
        String firstElementText = scrollViewPage.getFirstElementText();
        Assert.assertTrue(scrollViewPage.scrollToBottom(),
                "Should scroll to the bottom of the content");
        Assert.assertTrue(scrollViewPage.scrollToTop(),
                "Should scroll back to the top of the content");
        Assert.assertTrue(scrollViewPage.isElementWithTextDisplayed(firstElementText),
                "First element should be visible again after scrolling to top");
    }
}
