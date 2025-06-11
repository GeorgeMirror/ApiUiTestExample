package org.example.ui.tests;

import io.qameta.allure.*;
import org.example.ui.pages.LoginPage;
import org.example.ui.testbase.TestBase;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

@Epic("DemoQA Login Tests")
@Feature("Login functionality")
public class LoginTests extends TestBase {

    LoginPage loginPage = new LoginPage();

    @Test
    @Story("Negative test with wrong credentials")
    @Description("Проверка, что при неправильном логине/пароле отображается ошибка")
    void invalidLoginTest() {
        loginPage
                .setUsername("wronguser")
                .setPassword("wrongpass")
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("Empty fields test")
    @Description("Проверка, что при пустых полях отображается ошибка")
    void emptyFieldsTest() {
        loginPage
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("UI elements test")
    @Description("Проверка отображения полей логина и пароля")
    void uiElementsVisibleTest() {
        loginPage.usernameInput.shouldBe(visible);
        loginPage.passwordInput.shouldBe(visible);
        loginPage.loginButton.shouldBe(visible);
    }

    @Test
    @Story("Test with only username")
    @Description("Проверка, что при вводе только логина и пустом пароле отображается ошибка")
    void onlyUsernameTest() {
        loginPage
                .setUsername("someuser")
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("Test with only password")
    @Description("Проверка, что при вводе только пароля и пустом логине отображается ошибка")
    void onlyPasswordTest() {
        loginPage
                .setPassword("somepassword")
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("Test with long username and password")
    @Description("Проверка, что при слишком длинных данных отображается ошибка")
    void longCredentialsTest() {
        String longUser = "a".repeat(1000);
        String longPass = "b".repeat(1000);
        loginPage
                .setUsername(longUser)
                .setPassword(longPass)
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("Test with SQL injection attempt")
    @Description("Проверка, что SQL-инъекции не проходят")
    void sqlInjectionTest() {
        loginPage
                .setUsername("' OR '1'='1")
                .setPassword("' OR '1'='1")
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("Test with special characters")
    @Description("Проверка, что спецсимволы в логине и пароле не вызывают падения")
    void specialCharactersTest() {
        loginPage
                .setUsername("!@#$%^&*()")
                .setPassword("<>?/\\|}{")
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("Test login with whitespace-only fields")
    @Description("Проверка, что поля, заполненные только пробелами, не проходят авторизацию")
    void whitespaceOnlyFieldsTest() {
        loginPage
                .setUsername("   ")
                .setPassword("   ")
                .clickLogin()
                .checkError("Invalid username or password!");
    }

    @Test
    @Story("Test that Book Store API link is visible")
    @Description("Проверка, что на странице логина есть ссылка Book Store API")
    void bookStoreLinkVisibleTest() {
        loginPage.newUserButton.shouldBe(visible);
        loginPage.bookStoreLink.shouldHave(text("Book Store")).shouldBe(visible);
    }
}