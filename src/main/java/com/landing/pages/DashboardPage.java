package com.landing.pages;

import com.codeborne.selenide.SelenideElement;
import com.landing.utils.ConfigReader;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage extends BasePage {

    // Локаторы
    private final SelenideElement userMenu = $("[class*='user-menu'], .avatar, img[alt*='avatar']");
    private final SelenideElement marketOverview = $(byText("Marktübersicht"));

    // Тексты из config
    private final String dashboardTitle;
    private final String userName;

    public DashboardPage() {
        this.dashboardTitle = ConfigReader.get("dashboard.title");
        this.userName = ConfigReader.get("test.user.name");
    }

    @Step("Verify dashboard loaded")
    public DashboardPage verifyPageLoaded() {
        $(byText(dashboardTitle)).shouldBe(visible);
        return this;
    }

    @Step("Verify user logged in")
    public DashboardPage verifyUserLoggedIn() {
        userMenu.shouldBe(visible);
        $(byText(userName)).shouldBe(visible);
        return this;
    }

    @Step("Verify market overview visible")
    public DashboardPage verifyMarketOverviewVisible() {
        marketOverview.shouldBe(visible);
        return this;
    }

    @Step("Click logout")
    public DashboardPage clickLogout() {
        userMenu.click();
        // Ищем кнопку выхода по частичному совпадению текста
        $("a:contains('Abmelden'), a:contains('Logout'), button:contains('Sign Out')").click();
        return this;
    }

    @Step("Verify logged out - login button visible")
    public DashboardPage verifyLoggedOut() {
        $("a[href='/login']").shouldBe(visible);
        return this;
    }
}