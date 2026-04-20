package org.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.automation.utils.AppiumUtils;

/**
 * Singleton factory for managing AndroidDriver lifecycle.
 * Reads capabilities from config.properties and creates a single driver instance.
 */
public class DriverFactory {

    private static DriverFactory instance;
    private static AndroidDriver driver;
    private static AppiumDriverLocalService service;
    private static Properties properties;

    private DriverFactory() {
        loadConfig();
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
     * Loads configuration properties from config.properties file.
     *
     * @throws RuntimeException if the config file cannot be found or read.
     */
    private void loadConfig() {
        properties = new Properties();
        try {
            String configPath = System.getProperty("user.dir") + "//src//main//java//org//automation//resources//config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Starts the Appium server using the existing AppiumUtils logic.
     *
     * @throws RuntimeException if the server fails to start.
     */
    public void startService() {
        String ipAddress = System.getProperty("ipAddress") != null
                ? System.getProperty("ipAddress")
                : properties.getProperty("ipAddress");
        int port = Integer.parseInt(properties.getProperty("port"));
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
        String androidDeviceName = properties.getProperty("androidDeviceName");
        String appPackage = properties.getProperty("appPackage");
        String appActivity = properties.getProperty("appActivity");
        String apkFileName = properties.getProperty("apkFileName");
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
                Integer.parseInt(properties.getProperty("defaultWaitTimeout"))));
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

    /**
     * Returns the loaded properties for external access.
     *
     * @return The Properties instance loaded from config.properties.
     */
    public Properties getProperties() {
        return properties;
    }
}
