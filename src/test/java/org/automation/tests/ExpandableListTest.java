package org.automation.tests;

import org.automation.pages.ExpandableListPage;
import org.automation.pages.HomePage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Expandable List Test Suite.
 * Contains all test cases related to the Expandable Lists screen.
 * Navigation: Home -> Views -> Expandable Lists
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
                "Children should appear after expanding People Names");
        expandableListPage.tapDogNamesGroup();
        expandableListPage.tapPeopleNamesGroup();
        Assert.assertFalse(expandableListPage.arePeopleNamesChildrenDisplayed(),
                "Children should disappear after collapsing People Names");
    }
}
