package com.landing.tests.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.landing.config.SiteConfig;
import com.landing.config.SiteConfigFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    protected abstract String getSiteName();

    protected static String baseUrl;
    protected static String TEST_USER_EMAIL;
    protected static String TEST_USER_PASSWORD;

    @BeforeAll
    static void globalSetup() {
        // Настройка браузера один раз для всех тестов
        WebDriverManager.chromedriver().setup();
        Configuration.browserBinary = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        Configuration.browser = "chrome";
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

    @BeforeEach
    void setUp() {
        // Загружаем конфиг для конкретного сайта
        SiteConfigFactory.loadForSite(getSiteName());
        SiteConfig config = SiteConfigFactory.get();

        // Устанавливаем настройки из конфига
        Configuration.baseUrl = config.getBaseUrl();
        Configuration.headless = config.isHeadless();

        // Сохраняем в статические поля для совместимости
        baseUrl = config.getBaseUrl();
        TEST_USER_EMAIL = config.getTestUserEmail();
        TEST_USER_PASSWORD = config.getTestUserPassword();

        Selenide.open("/");
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
        SiteConfigFactory.clear();
    }

    // Удобный метод для доступа к полному конфигу
    protected SiteConfig config() {
        return SiteConfigFactory.get();
    }
}