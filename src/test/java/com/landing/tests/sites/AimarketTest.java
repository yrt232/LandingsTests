package com.landing.tests.sites;

import com.landing.tests.smoke.AbstractSmokeTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

/**
 * Тесты для сайта Aimarket
 */
@Epic("Aimarket")
@Feature("Smoke Tests")
public class AimarketTest extends AbstractSmokeTest {

    @Override
    protected String getSiteName() {
        return "aimarket";
    }
}