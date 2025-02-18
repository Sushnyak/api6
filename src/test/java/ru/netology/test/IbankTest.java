package ru.netology.test;


import com.codeborne.selenide.Selenide;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

public class IbankTest {

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulTransferFromSecondToFirst(){
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getFirsCard());
        var secondCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getSecondCard());
        int amount = DataHelper.generateAmount(secondCardBalanceStart);
        var transferPage = dashboardPage.selectCard(DataHelper.getFirsCard());
        transferPage.transferMoney(Integer.toString(amount), DataHelper.getSecondCard());
        var firstCardBalanceFinish = firstCardBalanceStart + amount;
        var secondCardBalanceFinish = secondCardBalanceStart - amount;
        Assert.assertEquals(dashboardPage.getCardBalance(DataHelper.getFirsCard()),
                firstCardBalanceFinish);
        Assert.assertEquals(dashboardPage.getCardBalance(DataHelper.getSecondCard()),
                secondCardBalanceFinish);
    }

    @Test
    void shouldSuccessfulTransferFromFirstToSecond(){
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getFirsCard());
        var secondCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getSecondCard());
        int amount = DataHelper.generateAmount(firstCardBalanceStart);
        var transferPage = dashboardPage.selectCard(DataHelper.getSecondCard());
        transferPage.transferMoney(Integer.toString(amount), DataHelper.getFirsCard());
        var firstCardBalanceFinish = firstCardBalanceStart - amount;
        var secondCardBalanceFinish = secondCardBalanceStart + amount;
        Assert.assertEquals(dashboardPage.getCardBalance(DataHelper.getFirsCard()),
                firstCardBalanceFinish);
        Assert.assertEquals(dashboardPage.getCardBalance(DataHelper.getSecondCard()),
                secondCardBalanceFinish);
    }

    @Test
    void shouldReturnErrorMassageIfAmountMoreBalance() {
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getFirsCard());
        var secondCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getSecondCard());
        int amount = DataHelper.generateInvalidAmount(secondCardBalanceStart);
        var transferPage = dashboardPage.selectCard(DataHelper.getFirsCard());
        transferPage.transferMoney(Integer.toString(amount), DataHelper.getSecondCard());
        transferPage.checkErrorMassage();
    }

    @Test
    void shouldReturnErrorIfNoCardNumber() {
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getFirsCard());
        var secondCardBalanceStart = dashboardPage.getCardBalance(DataHelper.getSecondCard());
        int amount = DataHelper.generateAmount(secondCardBalanceStart);
        var transferPage = dashboardPage.selectCard(DataHelper.getFirsCard());
        transferPage.transferMoneyNoCardNumber(Integer.toString(amount));
        transferPage.checkErrorMassage();
    }

    @Test
    void shouldReturnErrorIfNoAmount() {
        var info = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCode();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(info);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var transferPage = dashboardPage.selectCard(DataHelper.getFirsCard());
        transferPage.transferMoneyNoAmount(DataHelper.getSecondCard());
        transferPage.checkErrorMassage();
    }
}
