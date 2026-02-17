package com.landing.tests.base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.landing.config.SiteConfig;
import com.landing.config.SiteConfigFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Базовый класс для всех тестов лендингов.
 * Каждый наследник реализует getSiteName() — возвращает имя тестируемого сайта.
 */
public abstract class BaseTest {

    /**
     * Наследник возвращает имя сайта: profiteco, lumix, glenford...
     * Это имя используется для загрузки правильного конфига.
     */
    protected abstract String getSiteName();

    /**
     * Удобный доступ к конфигу текущего сайта.
     * Используй в тестах: config().getBaseUrl(), config().getTestUserEmail()...
     */
    protected SiteConfig config() {
        return SiteConfigFactory.get();
    }

    @BeforeEach
    void setUp() {
        // 1. Загружаем конфиг для этого сайта
        SiteConfigFactory.loadForSite(getSiteName());
        SiteConfig config = config();

        // 2. Настраиваем Selenide из конфига
        Configuration.baseUrl = config.getBaseUrl();
        Configuration.headless = config.isHeadless();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.timeout = config.getTimeout();
        Configuration.pageLoadTimeout = 30000;

        // 3. Настраиваем браузер (один раз за сессию)
        if (Configuration.browser == null) {
            WebDriverManager.chromedriver().setup();

            // Пробуем твой путь, если не сработает — WebDriverManager сам найдёт
            try {
                Configuration.browserBinary = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
                Configuration.browser = "chrome";
            } catch (Exception e) {
                // Если путь не сработал, WebDriverManager использует системный Chrome
                Configuration.browser = "chrome";
            }

            Configuration.browserCapabilities = new org.openqa.selenium.chrome.ChromeOptions()
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage")
                    .addArguments("--disable-gpu");

            // Подключаем Allure
            SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                    .screenshots(true)
                    .savePageSource(true));
        }

        // 4. Открываем сайт
        Selenide.open("/");
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
        SiteConfigFactory.clear(); // Очищаем ThreadLocal
    }
}