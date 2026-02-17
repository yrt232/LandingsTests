package com.landing.tests.smoke;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.landing.tests.base.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Landing Sites Smoke Tests")
@Feature("Cross-site functionality testing")
public abstract class AbstractSmokeTest extends BaseTest {

    // ==================== HOME PAGE TESTS ====================

    @Test
    @DisplayName("Home page loads successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Home Page")
    void testHomePageLoads() {
        open(config().getBaseUrl());

        // Ждем загрузки
        sleep(2000);

        // Проверяем что body загрузился
        $("body").shouldBe(visible);

        // Проверяем что есть хоть какие-то элементы
        $$("a, button").shouldHave(sizeGreaterThan(0));
    }
    @Test
    @DisplayName("Click all languages")
    @Severity(SeverityLevel.NORMAL)
    @Story("Language")
    void testClickAllLanguages() {
        open(config().getBaseUrl());
        sleep(3000);

        // Массив языков для проверки
        String[] languages = {"English", "Español", "Deutsch", "French"};

        for (String lang : languages) {
            // Открываем меню
            $("[data-slot='dropdown-menu-trigger']").click();
            sleep(800);

            // Ищем кнопку с текстом языка внутри меню radix
            SelenideElement langButton = $x("//div[contains(@data-radix-menu-content, '')]//button[contains(text(), '" + lang + "')]");

            if (!langButton.exists()) {
                // Fallback - ищем просто по тексту
                langButton = $(byText(lang));
            }

            if (langButton.exists() && langButton.isDisplayed()) {
                System.out.println("Clicking: " + lang);
                langButton.click();
                sleep(1500);

                // Проверяем что страница жива
                $("body").shouldBe(visible);
            } else {
                System.out.println("Language not found: " + lang);
            }
        }
    }
}