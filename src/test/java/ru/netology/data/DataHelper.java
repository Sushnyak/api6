package ru.netology.data;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import ru.netology.page.DashboardPage;
import ru.netology.page.TransferPage;

public class DataHelper {

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static CardInfo getFirsCard(){
        return new CardInfo("5559 0000 0000 0001",
                "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCard(){
        return new CardInfo("5559 0000 0000 0002",
                "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }

    public static int generateAmount(CardInfo cardInfo){
        var dashboardPage = new DashboardPage();
        int minValue = 0;
        int maxValue = dashboardPage.getCardBalance(cardInfo);
        int randomValue = minValue + (int) (Math.random() * (maxValue - minValue + 1));
        return randomValue;
    }

    @Value
    public static class AuthInfo{
        String login;
        String password;
    }

    @Value
    public static class VerificationCode{
        String code;
    }

    @Value
    public static class CardInfo{
        String cardNumber;
        String dataTestId;
    }
}
