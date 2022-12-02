package ru.netology.page;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("div.Login_loginForm__1Hg13 input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login'] span.button__text");
    private SelenideElement errorMessageOfLogin = $("[data-test-id='error-notification'] div.notification__content");


}
