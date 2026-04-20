package org.automation.tests;

import org.automation.pages.HomePage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Home Screen Test Suite.
 */
public class HomeTest extends AndroidBaseClass {

    @Test(description = "[TC-001] Verify home screen loads with all categories and tap Views",
            retryAnalyzer = RetryAnalyzer.class)
    public void testAppLaunchAndHomeScreen() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);

        Assert.assertTrue(homePage.areAllCategoriesDisplayed());
        homePage.openViewsPage();
        Assert.assertTrue(viewsPage.isExpandableListsDisplayed());
    }
}
