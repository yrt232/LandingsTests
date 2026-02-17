package com.landing.tests.smoke;

import com.landing.pages.LoginPage;
import com.landing.tests.base.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Smoke Tests")
@Feature("Site Availability")
public class SiteAvailabilityTest extends BaseTest {

    @Override
    protected String getConfigFileName() {
        // Будет переопределено через параметр или профиль
        return System.getProperty("test.config", "profiteco-config.properties");
    }

    @Test
    @DisplayName("Site is accessible and login page loads")
    @Severity(SeverityLevel.BLOCKER)
    void testSiteIsAccessible() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .verifyPageLoaded();
    }
}