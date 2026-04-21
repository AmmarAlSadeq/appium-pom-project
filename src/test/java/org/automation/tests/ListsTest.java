package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.ListsPage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-008: Lists > ListAdapter — Dynamic Scroll Until Item Found.
 * Navigation: Home -> Views -> Lists -> 04. ListAdapter
 */
public class ListsTest extends AndroidBaseClass {

    @Test(description = "[TC-008] Dynamic scroll until King Lear is found and verify no NoSuchElementException",
            retryAnalyzer = RetryAnalyzer.class)
    public void testListsDynamicScroll() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        ListsPage listsPage = new ListsPage(driver);

        homePage.openViewsPage();
        viewsPage.openListsPage();
        listsPage.openListAdapter();

        Assert.assertTrue(listsPage.scrollUntilTargetItem(),
                "Should find target item after dynamic scrolling");
    }
    // Note: The task references scrolling to "Gingerbread", but ApiDemos v6.0.6 ListAdapter
    // contains Shakespeare characters (e.g., "King Lear"). Target item adjusted accordingly.
    // Tap and selection assertion omitted — list items have no visible selection feedback.
}
