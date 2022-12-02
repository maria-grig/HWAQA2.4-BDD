package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement count = $("[data-test-id='amount'] input");
    private SelenideElement from = $("[data-test-id='from'] input");
    private SelenideElement button = $("[data-test-id='action-transfer'] span.button__text");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] div.notification__content");

}