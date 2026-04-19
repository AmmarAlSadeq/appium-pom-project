package org.automation.tests;

import org.automation.pages.ExpandableListPage;
import org.automation.pages.HomePage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-004: Expandable Lists > Custom Adapter — Expand and Collapse Groups.
 * Navigation: Home -> Views -> Expandable Lists -> 1. Custom Adapter
 */
public class ExpandableListTest extends AndroidBaseClass {

    @Test(description = "[TC-004] Expand and collapse groups in custom adapter expandable list",
            retryAnalyzer = RetryAnalyzer.class)
    public void testExpandAndCollapseGroups() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        ExpandableListPage expandableListPage = new ExpandableListPage(driver);

        homePage.openViewsPage();
        viewsPage.openExpandableLists();
        expandableListPage.openCustomAdapter();

        expandableListPage.tapPeopleNamesGroup();
        Assert.assertTrue(expandableListPage.arePeopleNamesChildrenDisplayed(),
                "All children (Arnold, Barry, Chuck, David) should appear after expanding People Names");

        expandableListPage.tapDogNamesGroup();

        expandableListPage.tapPeopleNamesGroup();
        Assert.assertFalse(expandableListPage.arePeopleNamesChildrenDisplayed(),
                "All children should disappear after collapsing People Names");
    }
}
