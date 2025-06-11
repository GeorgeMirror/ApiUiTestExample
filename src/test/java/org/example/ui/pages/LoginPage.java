package org.example.ui.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    public SelenideElement usernameInput = $("#userName");
    public SelenideElement passwordInput = $("#password");
    public SelenideElement loginButton = $("#login");
    public SelenideElement errorMessage = $("#name");
    public SelenideElement newUserButton = $("#newUser");
    public SelenideElement bookStoreLink = $("span.text", 2);

    public LoginPage openPage() {
        open("/login");
        usernameInput.shouldBe(visible, Duration.ofSeconds(60)); // ожидание прямо в методе открытия
        return this;
    }

    public LoginPage setUsername(String username) {
        usernameInput.setValue(username);
        return this;
    }

    public LoginPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    public LoginPage clickLogin() {
        loginButton.click();
        return this;
    }

    public LoginPage checkError(String message) {
        errorMessage.shouldHave(text(message));
        return this;
    }
}