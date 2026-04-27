package org.automation.config;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.automation.utils.AppiumUtils;
import org.automation.utils.ConfigReader;

/**
 * Singleton factory for managing AndroidDriver lifecycle with parallel support.
 * Uses ThreadLocal drivers with per-thread sticky device assignment.
 * Drivers persist across test classes within the same thread and are only
 * destroyed at suite end to avoid port conflicts and session creation overhead.
 */
public class DriverFactory {

    private static final int DEVICE_COUNT = loadDeviceCount();
    private static DriverFactory instance;
    private static AppiumDriverLocalService service;
    private static final ConcurrentHashMap<Long, Integer> threadDeviceMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, AndroidDriver> allDrivers = new ConcurrentHashMap<>();
    private static final AtomicInteger threadCounter = new AtomicInteger(0);
    private static final ThreadLocal<AndroidDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {
    }

    private static int loadDeviceCount() {
        try {
            return Integer.parseInt(ConfigReader.get("deviceCount"));
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Returns the singleton instance of DriverFactory.
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
     * Creates and returns a thread-local AndroidDriver for the assigned device.
     * Each unique thread is permanently mapped to a device via ConcurrentHashMap.
     *
     * @return The AndroidDriver instance for the current thread.
     */
    public AndroidDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            long threadId = Thread.currentThread().getId();
            int index = threadDeviceMap.computeIfAbsent(threadId,
                    id -> threadCounter.getAndIncrement() % DEVICE_COUNT);
            System.out.println("[Thread-" + threadId + "] Assigned to device" + (index + 1));
            buildAndroidDriver(index);
        }
        return driverThreadLocal.get();
    }

    /**
     * Creates a new AndroidDriver targeting the device at the given index.
     *
     * @param index The device index (0-based) from the device pool.
     */
    private void buildAndroidDriver(int index) {
        String prefix = "device" + (index + 1) + ".";
        String deviceName = ConfigReader.get(prefix + "deviceName");
        String udid = ConfigReader.get(prefix + "udid");
        int systemPort = Integer.parseInt(ConfigReader.get(prefix + "systemPort"));
        String appPackage = ConfigReader.get("appPackage");
        String appActivity = ConfigReader.get("appActivity");
        String apkFileName = ConfigReader.get("apkFileName");
        String apkPath = System.getProperty("user.dir") + "//src//main//java//org//automation//resources//" + apkFileName;

        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(deviceName);
        options.setUdid(udid);
        options.setSystemPort(systemPort);
        options.setAppPackage(appPackage);
        options.setAppActivity(appActivity);
        options.setApp(apkPath);
        options.setNoReset(false);
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", 60000);
        options.setCapability("appium:adbExecTimeout", 60000);

        System.out.println("[Thread-" + Thread.currentThread().getId() + "] Creating driver for " + deviceName + " (" + udid + ")");

        AndroidDriver driver = new AndroidDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Integer.parseInt(ConfigReader.get("defaultWaitTimeout"))));
        driverThreadLocal.set(driver);
        allDrivers.put(Thread.currentThread().getId(), driver);
    }

    /**
     * Quits the AndroidDriver for the current thread with error handling.
     * The thread keeps its device assignment for the next class.
     */
    public void quitDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("[DriverFactory] Error quitting driver: " + e.getMessage());
            }
            driverThreadLocal.remove();
            allDrivers.remove(Thread.currentThread().getId());
        }
    }

    /**
     * Quits all active AndroidDriver instances across all threads.
     * Called once at suite end to clean up resources.
     */
    public void quitAllDrivers() {
        allDrivers.values().forEach(driver -> {
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("[DriverFactory] Error quitting driver: " + e.getMessage());
            }
        });
        allDrivers.clear();
        driverThreadLocal.remove();
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
