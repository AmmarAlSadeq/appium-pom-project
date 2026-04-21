package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.ListsPage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Lists Test Suite.
 * Contains all test cases related to the Lists screen.
 * Navigation: Home -> Views -> Lists
 */
public class ListsTest extends AndroidBaseClass {

    @Test(description = "[TC-008] Dynamic scroll until target item is found",
            retryAnalyzer = RetryAnalyzer.class)
    public void testListsDynamicScroll() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        ListsPage listsPage = new ListsPage(driver);

        homePage.openViewsPage();
        viewsPage.openListsPage();
        listsPage.openListAdapter();
        Assert.assertTrue(listsPage.scrollUntilTargetItem(),
                "Target item should be found after dynamic scrolling");
    }
}
