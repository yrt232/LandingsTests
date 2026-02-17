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

public abstract class BaseTest {

    protected abstract String getSiteName();

    protected SiteConfig config() {
        return SiteConfigFactory.get();
    }

    @BeforeEach
    void setUp() {
        SiteConfigFactory.loadForSite(getSiteName());
        SiteConfig config = config();

        Configuration.baseUrl = config.getBaseUrl();
        Configuration.headless = config.isHeadless();
        Configuration.browserSize = config.getBrowserSize();
        Configuration.timeout = config.getTimeout();
        Configuration.pageLoadTimeout = 30000;

        if (Configuration.browser == null) {
            WebDriverManager.chromedriver().setup();

            try {
                Configuration.browserBinary = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
                Configuration.browser = "chrome";
            } catch (Exception e) {
                Configuration.browser = "chrome";
            }

            Configuration.browserCapabilities = new org.openqa.selenium.chrome.ChromeOptions()
                    .addArguments("--no-sandbox")
                    .addArguments("--disable-dev-shm-usage")
                    .addArguments("--disable-gpu");

            SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                    .screenshots(true)
                    .savePageSource(true));
        }

        Selenide.open("/");
    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
        SiteConfigFactory.clear();
    }
}