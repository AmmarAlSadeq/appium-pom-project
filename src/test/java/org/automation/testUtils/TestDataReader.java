package org.automation.testUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Generic test data reader that loads JSON files and returns values by key.
 * Caches loaded data to avoid re-reading files.
 */
public class TestDataReader {

    private static final Map<String, Map<String, Object>> cache = new ConcurrentHashMap<>();

    /**
     * Loads a JSON file and returns the value for the given key.
     *
     * @param filePath Absolute or relative path to the JSON file.
     * @param key      The key to look up in the JSON root object.
     * @return The value associated with the key.
     */
    @SuppressWarnings("unchecked")
    public static String getData(String filePath, String key) {
        Map<String, Object> data = cache.computeIfAbsent(filePath, path -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(new File(path), Map.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to read test data from " + path, e);
            }
        });
        return (String) data.get(key);
    }
}
