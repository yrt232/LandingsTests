package com.landing.tests.sites;

import com.landing.tests.smoke.AbstractSmokeTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

/**
 * Тесты для сайта Profiteco
 */
@Epic("Profiteco")
@Feature("Smoke Tests")
public class ProfitecoTest extends AbstractSmokeTest {

    @Override
    protected String getSiteName() {
        return "profiteco";
    }
}