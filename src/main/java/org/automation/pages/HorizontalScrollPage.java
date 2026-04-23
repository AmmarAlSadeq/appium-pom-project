package org.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import org.automation.base.AndroidActions;
import org.automation.utils.SwipeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

/**
 * Page object for the HorizontalScrollView screen.
 */
public class HorizontalScrollPage extends AndroidActions {

    private final SwipeHelper swipeHelper;

    /**
     * Constructs a HorizontalScrollPage with the given driver.
     *
     * @param driver The AndroidDriver instance.
     */
    public HorizontalScrollPage(AndroidDriver driver) {
        super(driver);
        this.swipeHelper = new SwipeHelper(driver);
    }

    private WebElement scrollView() {
        return driver.findElement(By.className("android.widget.HorizontalScrollView"));
    }

    /**
     * Swipes left within the HorizontalScrollView bounds.
     */
    public void swipeLeft() {
        WebElement sv = scrollView();
        int y = sv.getRect().getY() + sv.getRect().getHeight() / 2;
        int startX = sv.getRect().getX() + sv.getRect().getWidth() - 50;
        int endX = sv.getRect().getX() + 50;
        swipeHelper.horizontalSwipe(startX, y, endX);
    }

    /**
     * Swipes right within the HorizontalScrollView bounds.
     */
    public void swipeRight() {
        WebElement sv = scrollView();
        int y = sv.getRect().getY() + sv.getRect().getHeight() / 2;
        int startX = sv.getRect().getX() + 50;
        int endX = sv.getRect().getX() + sv.getRect().getWidth() - 50;
        swipeHelper.horizontalSwipe(startX, y, endX);
    }

    /**
     * Captures a full screenshot and crops it to the HorizontalScrollView bounds.
     *
     * @return Cropped BufferedImage of the scroll view area.
     * @throws Exception if screenshot capture or cropping fails.
     */
    public BufferedImage captureScrollViewScreenshot() throws Exception {
        WebElement sv = scrollView();
        Rectangle rect = sv.getRect();
        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        BufferedImage fullScreenshot = ImageIO.read(new ByteArrayInputStream(screenshotBytes));
        return fullScreenshot.getSubimage(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    /**
     * Calculates the percentage of differing pixels between two images.
     *
     * @param img1 First image.
     * @param img2 Second image.
     * @return Difference ratio (0.0 to 1.0). Returns 1.0 if dimensions differ.
     */
    public double getImageDifferencePercent(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            return 1.0;
        }
        long diffPixels = 0;
        int totalPixels = img1.getWidth() * img1.getHeight();
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                    diffPixels++;
                }
            }
        }
        return (double) diffPixels / totalPixels;
    }

    private BufferedImage referenceScreenshot;

    /**
     * Captures a reference screenshot of the scroll view for later comparison.
     *
     * @throws Exception if screenshot capture fails.
     */
    public void captureReference() throws Exception {
        referenceScreenshot = captureScrollViewScreenshot();
    }

    /**
     * Swipes left and verifies the screen content has changed.
     *
     * @return true if screen changed beyond 3% threshold.
     * @throws Exception if screenshot capture fails.
     */
    public boolean isContentChangedAfterSwipeLeft() throws Exception {
        swipeLeft();
        BufferedImage after = captureScrollViewScreenshot();
        return getImageDifferencePercent(referenceScreenshot, after) > 0.03;
    }

    /**
     * Swipes right and verifies the screen content has restored to the reference.
     *
     * @return true if screen matches reference within 6% tolerance.
     * @throws Exception if screenshot capture fails.
     */
    public boolean isContentRestoredAfterSwipeRight() throws Exception {
        swipeRight();
        BufferedImage after = captureScrollViewScreenshot();
        return getImageDifferencePercent(referenceScreenshot, after) < 0.06;
    }
}
