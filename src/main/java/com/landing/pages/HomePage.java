package com.landing.pages;

import com.codeborne.selenide.SelenideElement;
import com.landing.config.SiteConfigFactory;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class HomePage extends BasePage {

    private final SelenideElement loginButton = $("a[href='/login']");
    private final SelenideElement registerButton = $("a[href='/register']");
    private final SelenideElement languageDropdown = $("[data-slot='dropdown-menu-trigger']");

    public HomePage() {
    }

    @Step("Open home page")
    public HomePage openPage() {
        open(SiteConfigFactory.get().getBaseUrl());
        return this;
    }

    @Step("Verify home page loaded")
    public HomePage verifyPageLoaded() {
        $(byText(SiteConfigFactory.get().getDashboardTitle())).shouldBe(visible);
        $(byText(SiteConfigFactory.get().getLoginButtonText())).shouldBe(visible);
        $(byText(SiteConfigFactory.get().getLoginRegisterText())).shouldBe(visible);
        return this;
    }

    @Step("Click login button")
    public HomePage clickLogin() {
        $(byText(SiteConfigFactory.get().getLoginButtonText())).click();
        return this;
    }

    @Step("Click register button")
    public HomePage clickRegister() {
        $(byText(SiteConfigFactory.get().getLoginRegisterText())).click();
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