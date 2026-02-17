package com.landing.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    public static void load(String configFileName) {
        properties = new Properties();
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(configFileName)) {
            if (is == null) {
                throw new RuntimeException("Config file not found: " + configFileName);
            }
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + configFileName, e);
        }
    }

    public static String get(String key) {
        if (properties == null) {
            throw new IllegalStateException("Config not loaded. Call load() first.");
        }
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key not found in config: " + key);
        }
        return value;
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getTestUserEmail() {
        return get("test.user.email");
    }

    public static String getTestUserPassword() {
        return get("test.user.password");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }
}