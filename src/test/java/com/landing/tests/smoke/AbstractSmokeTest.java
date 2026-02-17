package com.landing.tests.smoke;

import com.github.javafaker.Faker;
import com.landing.pages.DashboardPage;
import com.landing.pages.HomePage;
import com.landing.pages.LoginPage;
import com.landing.pages.RegistrationPage;
import com.landing.tests.base.BaseTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

@Epic("Landing Sites Smoke Tests")
@Feature("Cross-site functionality testing")
public abstract class AbstractSmokeTest extends BaseTest {

    private final Faker faker = new Faker();

    // ==================== HOME PAGE TESTS ====================

    @Test
    @DisplayName("Home page loads successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Home page")
    void testHomePageLoadsSuccessfully() {
        HomePage homePage = new HomePage();
        homePage.verifyPageLoaded();
    }

    @Test
    @DisplayName("Navigate to login from home page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Home page navigation")
    void testNavigateToLoginFromHome() {
        HomePage homePage = new HomePage();
        homePage.clickLogin();

        LoginPage loginPage = new LoginPage();
        loginPage.verifyPageLoaded();
    }

    @Test
    @DisplayName("Navigate to registration from home page")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Home page navigation")
    void testNavigateToRegisterFromHome() {
        HomePage homePage = new HomePage();
        homePage.clickRegister();

        RegistrationPage regPage = new RegistrationPage();
        regPage.verifyPageLoaded();
    }

    // ==================== LOGIN TESTS ====================

    @Test
    @DisplayName("Login page loads successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login page")
    void testLoginPageLoadsSuccessfully() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage().verifyPageLoaded();
    }

    @Test
    @DisplayName("Successful login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login functionality")
    void testSuccessfulLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .enterEmail(config().getTestUserEmail())
                .enterPassword(config().getTestUserPassword())
                .clickLogin();

        DashboardPage dashboard = new DashboardPage();
        dashboard.verifyPageLoaded();
    }

    @Test
    @DisplayName("Login fails with invalid password")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login validation")
    void testLoginFailsWithInvalidPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .enterEmail(config().getTestUserEmail())
                .enterPassword("wrongpassword123")
                .clickLogin()
                .verifyErrorDisplayed()
                .verifyStillOnLoginPage();
    }

    @Test
    @DisplayName("Login fails with invalid email")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login validation")
    void testLoginFailsWithInvalidEmail() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .enterEmail("invalid@email.com")
                .enterPassword(config().getTestUserPassword())
                .clickLogin()
                .verifyErrorDisplayed()
                .verifyStillOnLoginPage();
    }

    @Test
    @DisplayName("Login fails with empty fields")
    @Severity(SeverityLevel.NORMAL)
    @Story("Login validation")
    void testLoginWithEmptyFields() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .clickLogin()
                .verifyErrorDisplayed()
                .verifyStillOnLoginPage();
    }

    @Test
    @DisplayName("Navigate to registration from login page")
    @Severity(SeverityLevel.MINOR)
    @Story("Login page navigation")
    void testNavigateToRegisterFromLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage()
                .clickRegister()
                .verifyRegisterPageOpened();
    }

    // ==================== REGISTRATION TESTS ====================

    @Test
    @DisplayName("Registration page loads successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Registration page")
    void testRegistrationPageLoadsSuccessfully() {
        RegistrationPage regPage = new RegistrationPage();
        regPage.openPage().verifyPageLoaded();
    }

    @Test
    @DisplayName("Successful registration form filling")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Registration functionality")
    void testSuccessfulRegistrationFormFilling() {
        open(config().getBaseUrl());
        $("a[href='/register']").click();

        String firstName = "Test" + faker.name().firstName();
        String lastName = "Test" + faker.name().lastName();
        String email = "test" + faker.number().randomNumber(8, true) + "@test.com";
        String username = "test" + faker.name().username().replaceAll("[^a-zA-Z0-9]", "");
        String password = "Test123!";

        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#email").setValue(email);
        $("#username").setValue(username);
        $("#password").setValue(password);
        $("#phoneNumber").setValue(faker.phoneNumber().cellPhone());

        selectCountry("Germany");
        selectLanguage("English");
        selectDateOfBirth("15", "3", "1990");

        $("button[type='submit']").click();

        webdriver().shouldHave(urlContaining("register"));
    }

    @Test
    @DisplayName("Registration fails with empty required fields")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Registration validation")
    void testRegistrationWithEmptyFields() {
        open(config().getBaseUrl());
        $("a[href='/register']").click();

        $("button[type='submit']").click();

        $(".error, .alert, .notification, .text-red-400").shouldBe(visible);
        $("button[type='submit']").shouldBe(visible);
    }

    @Test
    @DisplayName("Registration fails with invalid email format")
    @Severity(SeverityLevel.NORMAL)
    @Story("Registration validation")
    void testRegistrationWithInvalidEmail() {
        open(config().getBaseUrl());
        $("a[href='/register']").click();

        $("#firstName").setValue("TestJohn");
        $("#lastName").setValue("TestDoe");
        $("#email").setValue("invalid-email-format");
        $("#username").setValue("testuser123");
        $("#password").setValue("Test123!");

        $("button[type='submit']").click();

        $(".error, .alert, .notification, .text-red-400").shouldBe(visible);
        $("button[type='submit']").shouldBe(visible);
    }

    @Test
    @DisplayName("Registration fails with existing email")
    @Severity(SeverityLevel.NORMAL)
    @Story("Registration validation")
    void testRegistrationWithExistingEmail() {
        open(config().getBaseUrl());
        $("a[href='/register']").click();

        $("#firstName").setValue("Test");
        $("#lastName").setValue("User");
        $("#email").setValue(config().getTestUserEmail());
        $("#username").setValue("testuser" + faker.number().randomNumber(5, true));
        $("#password").setValue("TestPass123!");
        $("#phoneNumber").setValue(faker.phoneNumber().cellPhone());

        selectCountry("Germany");
        selectLanguage("English");
        selectDateOfBirth("15", "3", "1990");

        $("button[type='submit']").click();

        $(".error, .alert, .notification, .text-red-400").shouldBe(visible);
        $("button[type='submit']").shouldBe(visible);
    }

    @Test
    @DisplayName("Registration fails with underage user")
    @Severity(SeverityLevel.NORMAL)
    @Story("Registration validation")
    void testRegistrationWithUnderageUser() {
        open(config().getBaseUrl());
        $("a[href='/register']").click();

        $("#firstName").setValue("Test");
        $("#lastName").setValue("User");
        $("#email").setValue("test" + faker.number().randomNumber(8, true) + "@test.com");
        $("#username").setValue("testuser" + faker.number().randomNumber(5, true));
        $("#password").setValue("TestPass123!");
        $("#phoneNumber").setValue(faker.phoneNumber().cellPhone());

        selectCountry("Germany");
        selectLanguage("English");
        selectDateOfBirth("15", "3", "2010"); // 14 лет

        $("button[type='submit']").click();

        $(".error, .alert, .notification, .text-red-400").shouldBe(visible);
        $("button[type='submit']").shouldBe(visible);
    }

    @Test
    @DisplayName("Registration fails with weak password")
    @Severity(SeverityLevel.NORMAL)
    @Story("Registration validation")
    void testRegistrationWithWeakPassword() {
        open(config().getBaseUrl());
        $("a[href='/register']").click();

        $("#firstName").setValue("Test");
        $("#lastName").setValue("Test");
        $("#email").setValue("test" + faker.number().randomNumber(8, true) + "@test.com");
        $("#username").setValue("testuser" + faker.number().randomNumber(5, true));
        $("#password").setValue("13"); // Слабый пароль
        $("#phoneNumber").setValue(faker.phoneNumber().cellPhone());

        selectCountry("Germany");
        selectLanguage("English");
        selectDateOfBirth("15", "3", "1990");

        $("button[type='submit']").click();

        $(".error, .alert, .notification, .text-red-400").shouldBe(visible);
        $("button[type='submit']").shouldBe(visible);
    }

    @Test
    @DisplayName("Navigate to login from registration page")
    @Severity(SeverityLevel.MINOR)
    @Story("Registration page navigation")
    void testNavigateToLoginFromRegistration() {
        RegistrationPage regPage = new RegistrationPage();
        regPage.openPage()
                .clickLoginLink()
                .verifyLoginPageOpened();
    }

    // ==================== HELPER METHODS ====================

    @Step("Select country: {countryName}")
    private void selectCountry(String countryName) {
        $("form > div:nth-child(6) > button, [data-dropdown='country'] button").click();
        sleep(300);
        $(byText(countryName)).click();
        sleep(300);
    }

    @Step("Select language: {languageName}")
    private void selectLanguage(String languageName) {
        $("form > div:nth-child(7) > button, [data-dropdown='language'] button").click();
        sleep(300);
        $(byText(languageName)).click();
        sleep(300);
    }

    @Step("Select date of birth: {day}.{month}.{year}")
    private void selectDateOfBirth(String day, String month, String year) {
        $(byText("Datum auswählen")).click();
        sleep(500);
        $("select[aria-label='Choose the Year']").selectOptionByValue(year);
        $("select[aria-label='Choose the Month']").selectOptionByValue(month);
        $(byText(day)).click();
        sleep(300);
    }
}