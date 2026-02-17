package com.landing.tests.smoke;

import com.landing.pages.HomePage;
import com.landing.tests.base.BaseTest;
import com.landing.utils.ConfigReader;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Epic("Smoke Tests")
@Feature("Language Switch")
public class LanguageSwitchTest extends BaseTest {

    @Override
    protected String getConfigFileName() {
        return "profiteco-config.properties"; // дефолт, будет переопределено
    }

    @ParameterizedTest
    @ValueSource(strings = {"profiteco", "lumix", "glenford", "aimarket", "claimeazy", "zenithtrusts"})
    @DisplayName("Language switch works for {0}")
    @Severity(SeverityLevel.CRITICAL)
    void testLanguageSwitchForAllSites(String site) throws IOException {
        // Перезагружаем config для каждого сайта
        loadConfig(site + "-config.properties");

        HomePage homePage = new HomePage();
        homePage.openPage()
                .verifyPageLoaded()
                .openLanguageDropdown()
                .selectLanguage("English");

        // Проверяем что немецких слов нет
        $("body").shouldNotHave(text("Anmelden"));
        $("a[href='/login']").shouldBe(visible);
    }
}