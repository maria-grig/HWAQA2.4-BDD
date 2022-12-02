package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashBoardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.clearBrowserLocalStorage;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.devtools.v105.network.Network.clearBrowserCookies;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void clear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }


    @Test
    void shouldSuccessfullyRefillBalanceFirstCard() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        DashBoardPage dashBoardPage1 = verificationPage.validVerify(DataHelper.getCode(DataHelper.getAuthInfo()));
        int firstBalanceCard1 = dashBoardPage1.getFirstCardBalance();
        int firstBalanceCard2 = dashBoardPage1.getSecondCardBalance();
        int sum = DataHelper.generateValidAmount(firstBalanceCard2);
        MoneyTransferPage moneyTransferPage = dashBoardPage1.replenishCard1();
        DashBoardPage dashBoardPage2 = moneyTransferPage.makeValidTransfer(Integer.toString(sum), DataHelper.getSecondCard().getNumber());
        int secondBalanceCard1 = dashBoardPage2.getFirstCardBalance();
        int secondBalanceCard2 = dashBoardPage2.getSecondCardBalance();
        Assertions.assertEquals(firstBalanceCard1 + sum, secondBalanceCard1);
        Assertions.assertEquals(firstBalanceCard2 - sum, secondBalanceCard2);
    }

    @Test
    void shouldSuccessfullyRefillBalanceSecondCard() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        DashBoardPage dashBoardPage1 = verificationPage.validVerify(DataHelper.getCode(DataHelper.getAuthInfo()));
        int firstBalanceCard1 = dashBoardPage1.getFirstCardBalance();
        int firstBalanceCard2 = dashBoardPage1.getSecondCardBalance();
        int sum = DataHelper.generateValidAmount(firstBalanceCard1);
        MoneyTransferPage moneyTransferPage = dashBoardPage1.replenishCard2();
        DashBoardPage dashBoardPage2 = moneyTransferPage.makeValidTransfer(Integer.toString(sum), DataHelper.getFirstCard().getNumber());
        int secondBalanceCard1 = dashBoardPage2.getFirstCardBalance();
        int secondBalanceCard2 = dashBoardPage2.getSecondCardBalance();
        Assertions.assertEquals(firstBalanceCard1 - sum, secondBalanceCard1);
        Assertions.assertEquals(firstBalanceCard2 + sum, secondBalanceCard2);
    }

    @Test
    void shouldReturnErrorTransferOverCardBalance() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        DashBoardPage dashBoardPage = verificationPage.validVerify(DataHelper.getCode(DataHelper.getAuthInfo()));
        int firstBalanceCard2 = dashBoardPage.getSecondCardBalance();
        int sum = DataHelper.generateInvalidAmount(firstBalanceCard2);
        MoneyTransferPage moneyTransferPage = dashBoardPage.replenishCard1();
        moneyTransferPage.makeTransfer(Integer.toString(sum), DataHelper.getSecondCard().getNumber());
        moneyTransferPage.findErrorMessage("Ошибка! Попытка перевода суммы, превышающей лимит остатка на карте списания");

    }

    @Test
    void shouldReturnErrorWhenCardNumberFieldEmpty() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        DashBoardPage dashBoardPage = verificationPage.validVerify(DataHelper.getCode(DataHelper.getAuthInfo()));
        int firstBalanceCard2 = dashBoardPage.getSecondCardBalance();
        int sum = DataHelper.generateValidAmount(firstBalanceCard2);
        MoneyTransferPage moneyTransferPage = dashBoardPage.replenishCard1();
        moneyTransferPage.makeTransferWithoutCardNumber(Integer.toString(sum));
        moneyTransferPage.findErrorMessage("Ошибка! Произошла ошибка");
    }

    @Test
    void shouldReturnErrorForWrongCardNumber() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        DashBoardPage dashBoardPage1 = verificationPage.validVerify(DataHelper.getCode(DataHelper.getAuthInfo()));
        int firstBalanceCard2 = dashBoardPage1.getSecondCardBalance();
        int sum = DataHelper.generateValidAmount(firstBalanceCard2);
        MoneyTransferPage moneyTransferPage = dashBoardPage1.replenishCard1();
        moneyTransferPage.makeValidTransfer(Integer.toString(sum), DataHelper.getInvalidCard().getNumber());
        moneyTransferPage.findErrorMessage("Ошибка! Произошла ошибка");
    }

    @Test
    void shouldReturnErrorWhenZeroSum() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        DashBoardPage dashBoardPage = verificationPage.validVerify(DataHelper.getCode(DataHelper.getAuthInfo()));
        int sum = 0;
        MoneyTransferPage moneyTransferPage = dashBoardPage.replenishCard1();
        moneyTransferPage.makeValidTransfer(Integer.toString(sum), DataHelper.getSecondCard().getNumber());
        moneyTransferPage.findErrorMessage("Ошибка! Укажите сумму перевода");
    }

}
