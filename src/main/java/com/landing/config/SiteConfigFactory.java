package com.landing.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Фабрика для создания и хранения конфигураций сайтов.
 * Thread-safe — использует ThreadLocal для изоляции потоков.
 */
public class SiteConfigFactory {

    // ThreadLocal хранит конфиг для каждого потока отдельно
    private static final ThreadLocal<SiteConfig> currentConfig = new ThreadLocal<>();

    /**
     * Загружает конфиг для сайта из properties-файла или переменных окружения.
     *
     * Приоритет:
     * 1. Переменные окружения (для CI/CD)
     * 2. Файл {siteName}-config.properties (для локальной разработки)
     *
     * @param siteName имя сайта: profiteco, lumix, glenford...
     */
    public static void loadForSite(String siteName) {
        // Сначала пробуем переменные окружения (CI/CD)
        SiteConfig configFromEnv = loadFromEnvironment(siteName);
        if (configFromEnv != null) {
            currentConfig.set(configFromEnv);
            return;
        }

        // Иначе читаем из файла (локально)
        String fileName = siteName + "-config.properties";
        Properties props = loadPropertiesFile(fileName);
        SiteConfig configFromFile = buildFromProperties(siteName, props);
        currentConfig.set(configFromFile);
    }

    /**
     * Возвращает текущий конфиг для этого потока.
     */
    public static SiteConfig get() {
        SiteConfig config = currentConfig.get();
        if (config == null) {
            throw new IllegalStateException(
                    "Config not loaded. Call SiteConfigFactory.loadForSite(\"siteName\") first."
            );
        }
        return config;
    }

    /**
     * Очищает конфиг после теста (освобождает память).
     */
    public static void clear() {
        currentConfig.remove();
    }

    /**
     * Проверяет, загружен ли конфиг.
     */
    public static boolean isLoaded() {
        return currentConfig.get() != null;
    }

    // ============ Приватные методы ============

    /**
     * Пробует загрузить из переменных окружения.
     * Возвращает null, если не найдены необходимые переменные.
     */
    private static SiteConfig loadFromEnvironment(String siteName) {
        String prefix = siteName.toUpperCase() + "_";

        String baseUrl = System.getenv(prefix + "BASE_URL");
        String email = System.getenv(prefix + "EMAIL");
        String password = System.getenv(prefix + "PASSWORD");

        // Если нет обязательных полей — не из env
        if (baseUrl == null || email == null || password == null) {
            return null;
        }

        return new SiteConfig(
                siteName,
                baseUrl,
                email,
                password,
                System.getenv(prefix + "USER_NAME"),
                Boolean.parseBoolean(System.getenv().getOrDefault(prefix + "HEADLESS", "true")),
                System.getenv().getOrDefault(prefix + "BROWSER_SIZE", "1920x1080"),
                Integer.parseInt(System.getenv().getOrDefault(prefix + "TIMEOUT", "10000")),
                System.getenv().getOrDefault(prefix + "LANGUAGE", "de"),
                System.getenv().getOrDefault(prefix + "LOGIN_BUTTON_TEXT", "Sign In"),
                System.getenv().getOrDefault(prefix + "LOGIN_REGISTER_TEXT", "Sign Up"),
                System.getenv().getOrDefault(prefix + "LOGIN_BACK_TEXT", "Back"),
                System.getenv().getOrDefault(prefix + "LOGIN_TITLE", "Sign In"),
                System.getenv().getOrDefault(prefix + "LOGIN_SUBTITLE", "Welcome"),
                System.getenv().getOrDefault(prefix + "REGISTER_BUTTON_TEXT", "Create Account"),
                System.getenv().getOrDefault(prefix + "REGISTER_LOGIN_TEXT", "Sign In"),
                System.getenv().getOrDefault(prefix + "DASHBOARD_TITLE", "Dashboard"),
                System.getenv().getOrDefault(prefix + "ERROR_EMAIL_FORMAT", "Invalid email"),
                System.getenv().getOrDefault(prefix + "ERROR_PASSWORD_LENGTH", "Password too short")
        );
    }

    /**
     * Читает properties-файл из resources.
     */
    private static Properties loadPropertiesFile(String fileName) {
        Properties props = new Properties();
        try (InputStream is = SiteConfigFactory.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Config file not found: " + fileName +
                        ". Create it in src/test/resources/ or set environment variables.");
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config: " + fileName, e);
        }
        return props;
    }

    /**
     * Собирает SiteConfig из Properties.
     */
    private static SiteConfig buildFromProperties(String siteName, Properties props) {
        return new SiteConfig(
                siteName,
                getProperty(props, "base.url"),
                getProperty(props, "test.user.email"),
                getProperty(props, "test.user.password"),
                props.getProperty("test.user.name", "Test User"),
                Boolean.parseBoolean(props.getProperty("headless", "false")),
                props.getProperty("browser.size", "1920x1080"),
                Integer.parseInt(props.getProperty("timeout", "10000")),
                props.getProperty("language", "de"),
                props.getProperty("login.button.text", "Sign In"),
                props.getProperty("login.register.text", "Sign Up"),
                props.getProperty("login.back.text", "Back"),
                props.getProperty("login.title", "Sign In"),
                props.getProperty("login.subtitle", "Welcome"),
                props.getProperty("register.button.text", "Create Account"),
                props.getProperty("register.login.text", "Sign In"),
                props.getProperty("dashboard.title", "Dashboard"),
                props.getProperty("error.email.format", "Invalid email"),
                props.getProperty("error.password.length", "Password too short")
        );
    }

    /**
     * Безопасное получение обязательного свойства.
     */
    private static String getProperty(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Required property not found: " + key);
        }
        return value.trim();
    }
}