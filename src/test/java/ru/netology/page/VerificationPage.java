package ru.netology.page;


import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement codeButton = $("[data-test-id='action-verify'] span.button__text");
    private SelenideElement errorMessageOfVerification = $("[data-test-id='error-notification'] div.notification__content");

}
