package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id='code'] input");
    private SelenideElement codeButton = $("[data-test-id='action-verify'] span.button__text");
    private SelenideElement errorMessageOfVerification = $("[data-test-id='error-notification'] div.notification__content");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashBoardPage validVerify(DataHelper.VerificationInfo verificationInfo) {
        codeField.setValue(verificationInfo.getCode());
        codeButton.click();
        return new DashBoardPage();
    }

    public void invalidVerify(DataHelper.VerificationInfo verificationInfo) {
        codeField.setValue(verificationInfo.getCode());
        codeButton.click();
    }

    public void findErrorMessage(String expectedText) {
        errorMessageOfVerification.shouldHave(exactText(expectedText)).shouldBe(visible);
    }
}
