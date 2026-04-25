package org.automation.utils;

import java.io.File;
import java.time.Duration;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

/**
 * Utility class for common Appium-related operations.
 * This class provides methods for starting the Appium server and generating ExtentReports.
 */
public abstract class AppiumUtils {
    public AppiumDriverLocalService service;
    public static ExtentReports extentReport;

    /**
     * Starts the Appium server with the specified IP address and port.
     *
     * @param ipAddress The IP address to bind the Appium server to.
     * @param port      The port to run the Appium server on.
     * @return The started AppiumDriverLocalService instance.
     * @throws RuntimeException if the Appium server fails to start.
     */
    public AppiumDriverLocalService startAppiumServer(String ipAddress, int port) {
        String os = System.getProperty("os.name").toLowerCase();
        String appiumMainPath;
        if (os.contains("win")) {
            appiumMainPath = System.getProperty("user.home")
                    + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
        } else {
            appiumMainPath = System.getProperty("user.home")
                    + "/.npm-global/lib/node_modules/appium/build/lib/main.js";
        }

        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumMainPath))
                .withIPAddress(ipAddress)
                .usingPort(port)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withTimeout(Duration.ofSeconds(60))
                .build();

        service.start();
        return service;
    }

    /**
     * Configures and returns an ExtentReports object for generating test reports.
     *
     * @return The configured ExtentReports instance.
     */
    public static ExtentReports getReporterObject() {
        String reportPath = System.getProperty("user.dir") + "//src//reports//extent-report.html";
        File oldReport = new File(reportPath);
        if (oldReport.exists()) {
            oldReport.delete();
        }
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("ApiDemos Automation Report");
        reporter.config().setDocumentTitle("ApiDemos Automation Report");

        extentReport = new ExtentReports();
        extentReport.attachReporter(reporter);
        extentReport.setSystemInfo("QA Automation Lead", "Ammar AlSadeq");

        return extentReport;
    }

}