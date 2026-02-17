package com.landing.pages;

import com.codeborne.selenide.SelenideElement;
import com.landing.utils.ConfigReader;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class HomePage extends BasePage {

    // Локаторы — упрощённые, без contains
    private final SelenideElement loginButton = $("a[href='/login']");
    private final SelenideElement registerButton = $("a[href='/register']");
    private final SelenideElement languageDropdown = $("[data-slot='dropdown-menu-trigger']");

    // Тексты из config
    private final String loginButtonText;
    private final String registerButtonText;
    private final String mainTitle;

    public HomePage() {
        this.loginButtonText = ConfigReader.get("login.button.text");
        this.registerButtonText = ConfigReader.get("login.register.text");
        this.mainTitle = ConfigReader.get("dashboard.title");
    }

    @Step("Open home page")
    public HomePage openPage() {
        open(ConfigReader.getBaseUrl());
        return this;
    }

    @Step("Verify home page loaded")
    public HomePage verifyPageLoaded() {
        // Проверяем по тексту, а не по сложным селекторам
        $(byText(mainTitle)).shouldBe(visible);
        $(byText(loginButtonText)).shouldBe(visible);
        $(byText(registerButtonText)).shouldBe(visible);
        return this;
    }

    @Step("Click login button")
    public HomePage clickLogin() {
        $(byText(loginButtonText)).click();
        return this;
    }

    @Step("Click register button")
    public HomePage clickRegister() {
        $(byText(registerButtonText)).click();
        return this;
    }

    @Step("Open language dropdown")
    public HomePage openLanguageDropdown() {
        click(languageDropdown);
        return this;
    }

    @Step("Select language: {language}")
    public HomePage selectLanguage(String language) {
        $(byText(language)).shouldBe(visible).click();
        return this;
    }

    @Step("Verify language switched to: {expectedText}")
    public HomePage verifyLanguageSwitched(String expectedText) {
        $(byText(expectedText)).shouldBe(visible);
        return this;
    }
}