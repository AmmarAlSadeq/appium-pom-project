package org.automation.testUtils;

import io.appium.java_client.android.AndroidDriver;
import org.automation.config.DriverFactory;
import org.automation.utils.AppiumUtils;
import org.automation.utils.ConfigReader;
import org.automation.utils.ScrollHelper;
import org.automation.utils.SwipeHelper;
import org.automation.utils.WaitHelper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

/**
 * Base class for Android test automation.
 * Server lifecycle managed at suite level; driver per class; app reset per method.
 */
@Listeners(org.automation.testUtils.Listeners.class)
public class AndroidBaseClass extends AppiumUtils {

    public AndroidDriver driver;
    public WaitHelper waitHelper;
    public ScrollHelper scrollHelper;
    public SwipeHelper swipeHelper;

    /**
     * Starts Appium server once before the entire test suite.
     */
    @BeforeSuite(alwaysRun = true)
    public static void startAppiumServer() {
        DriverFactory factory = DriverFactory.getInstance();
        factory.startService();
    }

    /**
     * Creates AndroidDriver and initializes helpers for each test class.
     */
    @BeforeClass(alwaysRun = true)
    public void configureAppium() {
        DriverFactory factory = DriverFactory.getInstance();
        driver = factory.getDriver();

        waitHelper = new WaitHelper(driver);
        scrollHelper = new ScrollHelper(driver);
        swipeHelper = new SwipeHelper(driver);
    }

    /**
     * Resets the app before each test method to ensure clean state for retries.
     */
    @BeforeMethod(alwaysRun = true)
    public void resetApp() {
        if (driver != null) {
            String appPackage = ConfigReader.get("appPackage");
            driver.terminateApp(appPackage);
            driver.activateApp(appPackage);
        }
    }

    /**
     * Quits the driver after each test class.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverFactory factory = DriverFactory.getInstance();
        factory.quitDriver();
    }

    /**
     * Stops the Appium server after the entire test suite.
     */
    @AfterSuite(alwaysRun = true)
    public static void stopAppiumServer() {
        DriverFactory factory = DriverFactory.getInstance();
        factory.stopService();
    }
}
