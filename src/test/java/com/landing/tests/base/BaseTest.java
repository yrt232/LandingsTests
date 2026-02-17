package com.landing.tests.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.landing.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    // ThreadLocal для хранения имени сайта в каждом потоке
    protected static final ThreadLocal<String> currentSite = new ThreadLocal<>();

    protected static String baseUrl;
    protected static String TEST_USER_EMAIL;
    protected static String TEST_USER_PASSWORD;

    protected abstract String getConfigFileName();

    @BeforeEach
    void setUp() {
        // Загружаем config с текущим сайтом
        String site = currentSite.get();
        if (site != null) {
            ConfigReader.load(site + "-config.properties");

            baseUrl = ConfigReader.getBaseUrl();
            TEST_USER_EMAIL = ConfigReader.getTestUserEmail();
            TEST_USER_PASSWORD = ConfigReader.getTestUserPassword();
        }

        // Настройка браузера (только один раз)
        if (Configuration.browser == null) {
            WebDriverManager.chromedriver().setup();
            Configuration.browserBinary = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
            Configuration.browser = "chrome";
            Configuration.headless = ConfigReader.isHeadless();
            Configuration.browserSize = "1920x1080";
            Configuration.timeout = 10000;
            Configuration.pageLoadTimeout = 30000;

            Configuration.browserCapabilities = new org.openqa.selenium.chrome.ChromeOptions()
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage")
                    .addArguments("--disable-gpu");

            SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                    .screenshots(true)
                    .savePageSource(true));
        }

        Selenide.open(baseUrl);
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
        currentSite.remove();  // Очищаем после теста
    }
}