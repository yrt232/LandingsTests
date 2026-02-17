package com.landing.pages;

import com.codeborne.selenide.SelenideElement;
import com.landing.utils.ConfigReader;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage extends BasePage {

    // Локаторы
    private final SelenideElement firstNameInput = $("#firstName");
    private final SelenideElement lastNameInput = $("#lastName");
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement phoneInput = $("#phoneNumber");
    private final SelenideElement createButton = $("button[type='submit']");
    private final SelenideElement loginLink = $("a[href='/login']");

    // Тексты из config
    private final String createButtonText;
    private final String loginLinkText;

    public RegistrationPage() {
        this.createButtonText = ConfigReader.get("register.button.text");
        this.loginLinkText = ConfigReader.get("register.login.text");
    }

    @Step("Open registration page")
    public RegistrationPage openPage() {
        open(ConfigReader.getBaseUrl() + "/register");
        return this;
    }

    @Step("Verify registration page loaded")
    public RegistrationPage verifyPageLoaded() {
        verifyVisible(firstNameInput);
        verifyVisible(lastNameInput);
        verifyVisible(emailInput);
        verifyVisible(usernameInput);
        verifyVisible(passwordInput);
        verifyVisible(phoneInput);
        verifyVisible(createButton);
        verifyVisible(loginLink);

        createButton.shouldHave(text(createButtonText));
        loginLink.shouldHave(text(loginLinkText));

        return this;
    }

    @Step("Enter first name: {firstName}")
    public RegistrationPage enterFirstName(String firstName) {
        setValue(firstNameInput, firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public RegistrationPage enterLastName(String lastName) {
        setValue(lastNameInput, lastName);
        return this;
    }

    @Step("Enter email: {email}")
    public RegistrationPage enterEmail(String email) {
        setValue(emailInput, email);
        return this;
    }

    @Step("Enter username: {username}")
    public RegistrationPage enterUsername(String username) {
        setValue(usernameInput, username);
        return this;
    }

    @Step("Enter password")
    public RegistrationPage enterPassword(String password) {
        setValue(passwordInput, password);
        return this;
    }

    @Step("Enter phone: {phone}")
    public RegistrationPage enterPhone(String phone) {
        setValue(phoneInput, phone);
        return this;
    }

    @Step("Select country: {country}")
    public RegistrationPage selectCountry(String country) {
        // Клик по дропдауну страны
        $("form > div:nth-child(6) > button, [data-dropdown='country'] button").click();
        $(byText(country)).click();
        return this;
    }

    @Step("Select language: {language}")
    public RegistrationPage selectLanguage(String language) {
        // Клик по дропдауну языка
        $("form > div:nth-child(7) > button, [data-dropdown='language'] button").click();
        $(byText(language)).click();
        return this;
    }

    @Step("Select date of birth: {day}.{month}.{year}")
    public RegistrationPage selectDateOfBirth(String day, String month, String year) {
        $(byText("Datum auswählen")).click();
        $("select[aria-label='Choose the Year']").selectOptionByValue(year);
        $("select[aria-label='Choose the Month']").selectOptionByValue(month);
        $(byText(day)).click();
        return this;
    }

    @Step("Click create account")
    public RegistrationPage clickCreateAccount() {
        click(createButton);
        return this;
    }

    @Step("Click login link")
    public RegistrationPage clickLoginLink() {
        click(loginLink);
        return this;
    }

    // ==================== VERIFICATIONS ====================

    @Step("Verify error displayed")
    public RegistrationPage verifyErrorDisplayed() {
        $(".text-red-400, .error, .alert").shouldBe(visible);
        return this;
    }

    @Step("Verify still on registration page")
    public RegistrationPage verifyStillOnRegistrationPage() {
        firstNameInput.shouldBe(visible);
        return this;
    }

    @Step("Verify login page opened")
    public RegistrationPage verifyLoginPageOpened() {
        $("#emailOrUsername").shouldBe(visible);
        firstNameInput.shouldNotBe(visible);
        return this;
    }
}