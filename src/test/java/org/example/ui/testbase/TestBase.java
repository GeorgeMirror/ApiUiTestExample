package org.example.ui.testbase;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.example.config.TestConfig;
import org.example.ui.pages.LoginPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        TestConfig.configure();

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @BeforeEach
    void beforeEach() {
        LoginPage loginPage = new LoginPage();
        loginPage.openPage();
    }
}