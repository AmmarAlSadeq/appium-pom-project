package org.automation.config;

import java.time.Duration;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.automation.utils.AppiumUtils;
import org.automation.utils.ConfigReader;

/**
 * Singleton factory for managing AndroidDriver lifecycle.
 * Uses ConfigReader for capabilities and creates a single driver instance.
 */
public class DriverFactory {

    private static DriverFactory instance;
    private static AndroidDriver driver;
    private static AppiumDriverLocalService service;

    private DriverFactory() {
    }

    /**
     * Returns the singleton instance of DriverFactory.
     * Thread-safe double-checked locking.
     *
     * @return The singleton DriverFactory instance.
     */
    public static synchronized DriverFactory getInstance() {
        if (instance == null) {
            instance = new DriverFactory();
        }
        return instance;
    }

    /**
     * Starts the Appium server using the existing AppiumUtils logic.
     *
     * @throws RuntimeException if the server fails to start.
     */
    public void startService() {
        String ipAddress = System.getProperty("ipAddress") != null
                ? System.getProperty("ipAddress")
                : ConfigReader.get("ipAddress");
        int port = Integer.parseInt(ConfigReader.get("port"));
        AppiumUtils appiumUtils = new AppiumUtils() {};
        service = appiumUtils.startAppiumServer(ipAddress, port);

        if (service == null || !service.isRunning()) {
            throw new RuntimeException("Failed to start Appium server on " + ipAddress + ":" + port);
        }
        System.out.println("Appium server started at: " + service.getUrl());
    }

    /**
     * Creates and returns the AndroidDriver instance.
     * If driver already exists and is active, returns the same instance.
     *
     * @return The AndroidDriver instance.
     */
    public AndroidDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    /**
     * Creates a new AndroidDriver with UiAutomator2Options from config.
     */
    private void createDriver() {
        String androidDeviceName = ConfigReader.get("androidDeviceName");
        String appPackage = ConfigReader.get("appPackage");
        String appActivity = ConfigReader.get("appActivity");
        String apkFileName = ConfigReader.get("apkFileName");
        String apkPath = System.getProperty("user.dir") + "//src//main//java//org//automation//resources//" + apkFileName;

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(androidDeviceName);
        options.setAppPackage(appPackage);
        options.setAppActivity(appActivity);
        options.setApp(apkPath);
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(300));

        String serverUrl = service.getUrl().toString();
        System.out.println("Appium Server URL: " + serverUrl);
        System.out.println("APK Path: " + apkPath);

        driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(ConfigReader.get("defaultWaitTimeout"))));
    }

    /**
     * Quits the AndroidDriver and removes the reference.
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Stops the Appium server.
     */
    public void stopService() {
        if (service != null && service.isRunning()) {
            service.stop();
        }
    }

}
