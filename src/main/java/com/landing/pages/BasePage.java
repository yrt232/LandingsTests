package com.landing.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public abstract class BasePage {

    @Step("Verify element is visible")
    protected void verifyVisible(String selector) {
        $(selector).shouldBe(visible);
    }

    @Step("Verify element is visible")
    protected void verifyVisible(SelenideElement element) {
        element.shouldBe(visible);
    }

    @Step("Click element")
    protected void click(String selector) {
        $(selector).shouldBe(visible).click();
    }

    @Step("Click element")
    protected void click(SelenideElement element) {
        element.shouldBe(visible).click();
    }

    @Step("Set value {value}")
    protected void setValue(String selector, String value) {
        $(selector).shouldBe(visible).clear();
        $(selector).setValue(value);
    }

    @Step("Set value {value}")
    protected void setValue(SelenideElement element, String value) {
        element.shouldBe(visible).clear();
        element.setValue(value);
    }
}