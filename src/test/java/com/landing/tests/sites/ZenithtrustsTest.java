package com.landing.tests.sites;

import com.landing.tests.smoke.AbstractSmokeTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

/**
 * Тесты для сайта Zenithtrusts
 */
@Epic("Zenithtrusts")
@Feature("Smoke Tests")
public class ZenithtrustsTest extends AbstractSmokeTest {

    @Override
    protected String getSiteName() {
        return "zenithtrusts";
    }
}