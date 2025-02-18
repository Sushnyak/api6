package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Visible;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;

import java.util.Random;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountInput = $("[data-test-id=amount] input");
    private final SelenideElement cardWriteOff = $("[data-test-id=from] input");
    private final SelenideElement buttonTransfer = $("[data-test-id=action-transfer]");

    public TransferPage() {
        amountInput.shouldBe(visible);
    }

    public DashboardPage transferMoney(String amount, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amount);
        cardWriteOff.setValue(cardInfo.getCardNumber());
        buttonTransfer.click();
        return new DashboardPage();
    }

    public DashboardPage transferMoneyNoCardNumber(String amount) {
        amountInput.setValue(amount);
        buttonTransfer.click();
        return new DashboardPage();
    }

    public DashboardPage transferMoneyNoAmount(DataHelper.CardInfo cardInfo) {
        cardWriteOff.setValue(cardInfo.getCardNumber());
        buttonTransfer.click();
        return new DashboardPage();
    }

    private int generateAmount() {
        int amount = 0;
        var dashboardPage = new DashboardPage();
        int firstCardBalance = dashboardPage.getCardBalance(DataHelper.getFirsCard());
        int secondCardBalance = dashboardPage.getCardBalance(DataHelper.getSecondCard());
        if (firstCardBalance == secondCardBalance){
            return amount = 1500;
        } else if(firstCardBalance > secondCardBalance){
            return amount = 2000;
        } else {
            return amount = 1000;
        }
    }

    public void checkErrorMassage() {
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка!"))
                .should(visible);
    }
}
