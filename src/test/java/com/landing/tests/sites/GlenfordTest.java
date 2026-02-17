package com.landing.tests.sites;

import com.landing.tests.smoke.AbstractSmokeTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

/**
 * Тесты для сайта Glenford
 */
@Epic("Glenford")
@Feature("Smoke Tests")
public class GlenfordTest extends AbstractSmokeTest {

    @Override
    protected String getSiteName() {
        return "glenford";
    }
}