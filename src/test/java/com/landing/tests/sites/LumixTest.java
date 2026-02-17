package com.landing.tests.sites;

import com.landing.tests.smoke.AbstractSmokeTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

/**
 * Тесты для сайта Lumix
 */
@Epic("Lumix")
@Feature("Smoke Tests")
public class LumixTest extends AbstractSmokeTest {

    @Override
    protected String getSiteName() {
        return "lumix";
    }
}