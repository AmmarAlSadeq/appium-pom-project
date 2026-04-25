package org.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading configuration properties.
 * Loads config.properties once and provides static access to all key-value pairs.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            String configPath = System.getProperty("user.dir")
                    + "//src//main//java//org//automation//resources//config.properties";
            FileInputStream fis = new FileInputStream(configPath);
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the config value for the given key.
     *
     * @param key The property key.
     * @return The property value.
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }
}
