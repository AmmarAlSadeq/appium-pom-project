package org.automation.testUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.android.AndroidDriver;
import org.automation.config.DriverFactory;
import org.automation.utils.AppiumUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Custom TestNG listener for handling test execution events.
 * Integrates with ExtentReports and captures screenshots on failure.
 */
public class Listeners implements ITestListener {
    ExtentReports extentReport = AppiumUtils.getReporterObject();
    ExtentTest extentTest;

    /**
     * Creates a new ExtentTest entry when a test starts.
     *
     * @param result The test result containing the method being executed.
     */
    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReport.createTest(result.getMethod().getMethodName());
    }

    /**
     * Logs a PASS status when a test succeeds.
     *
     * @param result The test result for the passed test.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, "Test Passed");
    }

    /**
     * Logs a FAIL status and captures a screenshot when a test fails.
     *
     * @param result The test result containing the failure details.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.log(Status.FAIL, "Test Failed");
        extentTest.fail(result.getThrowable());

        try {
            AndroidDriver driver = DriverFactory.getInstance().getDriver();
            if (driver != null) {
                String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                extentTest.addScreenCaptureFromBase64String("data:image/png;base64," + screenshotBase64,
                        result.getName());
            }
        } catch (Exception e) {
            extentTest.warning("Could not capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Logs a SKIP status when a test is skipped.
     *
     * @param result The test result for the skipped test.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.log(Status.SKIP, "Test Skipped");
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
