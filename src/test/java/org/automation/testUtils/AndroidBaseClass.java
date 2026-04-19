package org.automation.testUtils;

import java.io.IOException;

import io.appium.java_client.android.AndroidDriver;
import org.automation.config.DriverFactory;
import org.automation.utils.AppiumUtils;
import org.automation.utils.ScrollHelper;
import org.automation.utils.SwipeHelper;
import org.automation.utils.WaitHelper;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base class for Android test automation.
 * Uses DriverFactory for driver lifecycle, keeps AppiumUtils server management.
 * Automatically assigns RetryAnalyzer to all test methods.
 */
public class AndroidBaseClass extends AppiumUtils implements IHookable {

    public AndroidDriver driver;
    public WaitHelper waitHelper;
    public ScrollHelper scrollHelper;
    public SwipeHelper swipeHelper;

    /**
     * Intercepts test execution to set RetryAnalyzer before each test runs.
     */
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        testResult.getMethod().setRetryAnalyzerClass(RetryAnalyzer.class);
        callBack.runTestMethod(testResult);
    }

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
     * Quits the driver and stops the Appium server.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverFactory factory = DriverFactory.getInstance();
        factory.quitDriver();
        factory.stopService();
    }
}
