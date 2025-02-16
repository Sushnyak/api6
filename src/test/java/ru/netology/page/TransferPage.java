package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

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

    public void checkErrorMassage() {
        $("Ошибка").shouldBe(visible);
    }
}
