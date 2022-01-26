package ru.netology.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.Login;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    public DashboardPage dashboardPage;

    @BeforeEach
    void setUp() {
        Configuration.headless = true;
        open("http://localhost:9999", Login.class);
        val login = new Login();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = login.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

    }

    @Test
    public void transferMoneyFirstToSecond() {
        int amount = 700;
        var firstCardData = DataHelper.CardData.getFirstCardData();
        var secondCardData = DataHelper.CardData.getSecondCardData();
        int firstCardBalanceBeforeTransfer = dashboardPage.getFirstCardCurrentBalance();
        int secondCardCurrentBalanceBeforeTransfer = dashboardPage.getSecondCardCurrentBalance();
        val moneyTransferPage = dashboardPage.secondCard();
        moneyTransferPage.moneyTransfer(firstCardData, amount);
        int firstCardBalanceAfterTransfer = DataHelper.balanceCardTransferFrom(firstCardBalanceBeforeTransfer, amount);
        int secondCArdBalanceAfterTransfer = DataHelper.balanceCardTransferTo(secondCardCurrentBalanceBeforeTransfer, amount);
        int firstCardNewBalance = dashboardPage.getFirstCardCurrentBalance();
        int secondCArdNewBalance = dashboardPage.getSecondCardCurrentBalance();
        assertEquals(firstCardBalanceAfterTransfer, firstCardNewBalance);
        assertEquals(secondCArdBalanceAfterTransfer, secondCArdNewBalance);

    }

    @Test
    public void transferZeroFromFirstToSecond() {
        int amount = 0;
        var firstCardData = DataHelper.CardData.getFirstCardData();
        val moneyTransferPage = dashboardPage.secondCard();
        moneyTransferPage.moneyTransfer(firstCardData, amount);
        moneyTransferPage.sumShouldNotBeZeroMethod();

    }

    @Test
    public void transferFromFirstToGoUnderThanLimit() {
        int amount = 30000;
        var firstCardData = DataHelper.CardData.getFirstCardData();
        val moneyTransferPage = dashboardPage.secondCard();
        moneyTransferPage.moneyTransfer(firstCardData, amount);
        moneyTransferPage.notEnoughMoneyForTransfer();
    }


}

