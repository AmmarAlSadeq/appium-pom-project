package org.automation.tests;

import org.automation.pages.DragDropPage;
import org.automation.pages.HomePage;
import org.automation.pages.ViewsPage;
import org.automation.testUtils.AndroidBaseClass;
import org.automation.testUtils.RetryAnalyzer;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC-003: Drag and Drop — Long-Press, Drag Dot & Verify EditText Updates.
 * Navigation: Home -> Views -> Drag and Drop
 */
public class DragDropTest extends AndroidBaseClass {

    @Test(description = "[TC-003] Drag and drop dot and verify EditText updates",
            retryAnalyzer = RetryAnalyzer.class)
    public void testDragAndDrop() {
        HomePage homePage = new HomePage(driver);
        ViewsPage viewsPage = new ViewsPage(driver);
        DragDropPage dragDropPage = new DragDropPage(driver);

        homePage.openViewsPage();
        viewsPage.openDragAndDrop();
        dragDropPage.dragDot1ToDot2();
        Assert.assertEquals(dragDropPage.getResultText(), "Dropped!");
    }
}
