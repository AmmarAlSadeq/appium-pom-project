package org.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration provider that loads config.properties once at class-load time
 * and exposes key-value pairs via static access.
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
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
