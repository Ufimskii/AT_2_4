package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private static SelenideElement firstBalance = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']");
    private static SelenideElement secondBalance = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']");
    private SelenideElement firstCard = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] [data-test-id='action-deposit']");
    private SelenideElement secondCard = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] [data-test-id=action-deposit]");

    public TransferPage firstCard() {
        firstCard.click();
        return new TransferPage();
    }

    public TransferPage secondCard() {
        secondCard.click();
        return new TransferPage();
    }

    public static int getFirstCardCurrentBalance() {
        String firstBalanceValue = firstBalance.getText();
        String firstCardBalance = firstBalanceValue.substring(29, firstBalanceValue.indexOf(" ", 29));
        return Integer.parseInt(firstCardBalance);
    }

    public static int getSecondCardCurrentBalance() {
        String secondBalanceValue = secondBalance.getText();
        String secondCardBalance = secondBalanceValue.substring(29, secondBalanceValue.indexOf(" ", 29));
        return Integer.parseInt(secondCardBalance);
    }
}
