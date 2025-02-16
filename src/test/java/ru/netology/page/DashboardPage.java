package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement header = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement buttonTransfer = $("[data-test-id=action-deposit]");

    public DashboardPage() {
        header.shouldBe(Condition.visible);
    }

    private SelenideElement getCard(DataHelper.CardInfo cardInfo){
        return cards.findBy(Condition.attribute("data-test-id", cardInfo.getDataTestId()));
    }

    public int getCardBalance(DataHelper.CardInfo cardInfo){
        SelenideElement cardElement = getCard(cardInfo);
        return extractBalance(cardElement.getText());
    }

    public TransferPage selectCard(DataHelper.CardInfo cardInfo){
        getCard(cardInfo).$("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
