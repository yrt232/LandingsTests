package com.landing.tests.sites;

import com.landing.tests.smoke.AbstractSmokeTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

/**
 * Тесты для сайта Claimeazy
 */
@Epic("Claimeazy")
@Feature("Smoke Tests")
public class ClaimeazyTest extends AbstractSmokeTest {

    @Override
    protected String getSiteName() {
        return "claimeazy";
    }
}