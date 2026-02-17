package com.landing.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LanguageSwitcher extends BasePage {

    // Локаторы
    private final SelenideElement languageDropdown = $("[data-dropdown='language'], [class*='language-selector']");

    @Step("Click language dropdown")
    public LanguageSwitcher openDropdown() {
        click(languageDropdown);
        return this;
    }

    @Step("Select language: {language}")
    public LanguageSwitcher selectLanguage(String language) {
        $(byText(language)).shouldBe(visible).click();
        return this;
    }

    @Step("Switch to German")
    public LanguageSwitcher switchToGerman() {
        openDropdown();
        selectLanguage("Deutsch");
        return this;
    }

    @Step("Switch to English")
    public LanguageSwitcher switchToEnglish() {
        openDropdown();
        selectLanguage("English");
        return this;
    }

    @Step("Switch to Spanish")
    public LanguageSwitcher switchToSpanish() {
        openDropdown();
        selectLanguage("Español");
        return this;
    }

    @Step("Verify current language is: {expectedLanguage}")
    public LanguageSwitcher verifyCurrentLanguage(String expectedLanguage) {
        // Проверяем что кнопка логина на нужном языке
        $(byText(expectedLanguage)).shouldBe(visible);
        return this;
    }
}