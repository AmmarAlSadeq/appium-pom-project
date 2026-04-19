package org.automation.testUtils;

import java.io.IOException;

import io.appium.java_client.android.AndroidDriver;
import org.automation.config.DriverFactory;
import org.automation.utils.AppiumUtils;
import org.automation.utils.ScrollHelper;
import org.automation.utils.SwipeHelper;
import org.automation.utils.WaitHelper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Base class for Android test automation.
 * Uses DriverFactory for driver lifecycle, keeps AppiumUtils server management.
 */
public class AndroidBaseClass extends AppiumUtils {

    public AndroidDriver driver;
    public WaitHelper waitHelper;
    public ScrollHelper scrollHelper;
    public SwipeHelper swipeHelper;

    /**
     * Starts Appium server and initializes AndroidDriver via DriverFactory.
     *
     * @throws IOException if config.properties cannot be read.
     */
    @BeforeClass(alwaysRun = true)
    public void configureAppium() throws IOException {
        DriverFactory factory = DriverFactory.getInstance();
        factory.startService();
        driver = factory.getDriver();

        waitHelper = new WaitHelper(driver);
        scrollHelper = new ScrollHelper(driver);
        swipeHelper = new SwipeHelper(driver);
    }

    /**
     * Resets the app to the home screen before each test method (including retries).
     */
    @BeforeMethod(alwaysRun = true)
    public void resetAppToHome() {
        driver.terminateApp("io.appium.android.apis");
        driver.activateApp("io.appium.android.apis");
    }

    /**
     * Quits the driver and stops the Appium server.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverFactory factory = DriverFactory.getInstance();
        factory.quitDriver();
        factory.stopService();
    }
}
