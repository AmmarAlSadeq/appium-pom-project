package org.automation.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import io.appium.java_client.android.AndroidDriver;
import org.automation.utils.AppiumUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Custom TestNG listener for handling test execution events.
 * Integrates with ExtentReports and captures screenshots on failure.
 */
public class Listeners implements ITestListener {

    private static final ExtentReports extentReport = AppiumUtils.getReporterObject();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "//src//reports//screenshots//";

    /**
     * Creates a new ExtentTest entry when a test starts.
     *
     * @param result The test result containing the method being executed.
     */
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReport.createTest(result.getMethod().getDescription() != null
                ? result.getMethod().getDescription()
                : result.getMethod().getMethodName());
        extentTest.set(test);
    }

    /**
     * Logs a PASS status when a test succeeds.
     *
     * @param result The test result for the passed test.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    /**
     * Logs a FAIL status and captures a screenshot when a test fails.
     *
     * @param result The test result containing the failure details.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test == null) {
            test = extentReport.createTest(result.getMethod().getDescription() != null
                    ? result.getMethod().getDescription()
                    : result.getMethod().getMethodName());
            extentTest.set(test);
        }
        test.log(Status.FAIL, "Test Failed");
        test.fail(result.getThrowable());

        try {
            AndroidDriver driver = (AndroidDriver) result.getInstance().getClass()
                    .getField("driver").get(result.getInstance());
            if (driver != null) {
                String sessionId = driver.getSessionId() != null ? driver.getSessionId().toString() : null;
                if (sessionId != null) {
                    File dir = new File(SCREENSHOT_DIR);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
                    String screenshotPath = SCREENSHOT_DIR + result.getName() + "_" + timestamp + ".png";
                    byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    try (FileOutputStream fos = new FileOutputStream(screenshotPath)) {
                        fos.write(screenshotBytes);
                    }
                    System.out.println("[Listeners] Screenshot saved: " + screenshotPath);
                    test.fail("Screenshot on failure",
                            MediaEntityBuilder.createScreenCaptureFromPath("screenshots/" + result.getName() + "_" + timestamp + ".png").build());
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("[Listeners] Could not access driver: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Listeners] Could not capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Logs a SKIP status when a test is skipped.
     *
     * @param result The test result for the skipped test.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extentTest.get();
        if (test == null) {
            test = extentReport.createTest(result.getMethod().getDescription() != null
                    ? result.getMethod().getDescription()
                    : result.getMethod().getMethodName());
            extentTest.set(test);
        }
        test.log(Status.SKIP, "Test Skipped");
        if (result.getThrowable() != null) {
            test.skip(result.getThrowable());
        }
    }

    /**
     * Flushes the ExtentReport after all tests finish.
     *
     * @param context The test context containing all test results.
     */
    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();
    }
}
