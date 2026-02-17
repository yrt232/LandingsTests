package com.landing.pages;

import com.codeborne.selenide.SelenideElement;
import com.landing.config.SiteConfigFactory;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage extends BasePage {

    private final SelenideElement emailInput = $("#emailOrUsername");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton = $("form button[type='submit']");
    private final SelenideElement registerLink = $("a[href='/register']");
    private final SelenideElement backToHomeLink = $("a[href='/']");

    public LoginPage() {
    }

    @Step("Open login page")
    public LoginPage openPage() {
        open(SiteConfigFactory.get().getBaseUrl() + "/login");
        return this;
    }

    @Step("Verify login page loaded")
    public LoginPage verifyPageLoaded() {
        verifyVisible(emailInput);
        verifyVisible(passwordInput);
        verifyVisible(loginButton);
        verifyVisible(registerLink);
        verifyVisible(backToHomeLink);

        loginButton.shouldHave(text(SiteConfigFactory.get().getLoginButtonText()));
        registerLink.shouldHave(text(SiteConfigFactory.get().getLoginRegisterText()));
        backToHomeLink.shouldHave(text(SiteConfigFactory.get().getLoginBackText()));

        return this;
    }

    @Step("Enter email: {email}")
    public LoginPage enterEmail(String email) {
        setValue(emailInput, email);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        setValue(passwordInput, password);
        return this;
    }

    @Step("Click login button")
    public LoginPage clickLogin() {
        click(loginButton);
        return this;
    }

    @Step("Click register link")
    public LoginPage clickRegister() {
        click(registerLink);
        return this;
    }

    @Step("Click back to home")
    public LoginPage clickBackToHome() {
        click(backToHomeLink);
        return this;
    }

    @Step("Login with credentials")
    public LoginPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return this;
    }

    @Step("Verify error displayed")
    public LoginPage verifyErrorDisplayed() {
        $(".text-red-400, .error, .alert").shouldBe(visible);
        return this;
    }

    @Step("Verify still on login page")
    public LoginPage verifyStillOnLoginPage() {
        emailInput.shouldBe(visible);
        return this;
    }

    @Step("Verify successful login")
    public LoginPage verifySuccessfulLogin() {
        emailInput.shouldNotBe(visible);
        passwordInput.shouldNotBe(visible);
        $(byText(SiteConfigFactory.get().getDashboardTitle())).shouldBe(visible);
        return this;
    }

    @Step("Verify register page opened")
    public LoginPage verifyRegisterPageOpened() {
        $("#firstName").shouldBe(visible);
        emailInput.shouldNotBe(visible);
        return this;
    }

    @Step("Verify home page opened")
    public LoginPage verifyHomePageOpened() {
        $(byText(SiteConfigFactory.get().getDashboardTitle())).shouldBe(visible);
        emailInput.shouldNotBe(visible);
        return this;
    }
}