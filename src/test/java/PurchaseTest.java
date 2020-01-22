import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTest {
    private String link = System.getProperty("url");;
    private int countBefore;
    private int countAfter;
    PageElements elements = new PageElements();
    Data.MonthAndYear monthAndYear = Data.getMonthAndYear();
    Data.ListCards listCards = Data.getListCards();


    @Test
    void shouldReturnIncorrectFormatError() {
        elements = new PageElements();
        countBefore = SqlUtils.countPayment();
        open(link);
        elements.buyButtonClick()
                .continueButtonClick()
                .allFieldsVisible();
        countAfter = SqlUtils.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnIncorrectFormatError_credit() {
        countBefore = SqlUtils.countCredit();
        open(link);
        elements.buyButtonClick()
                .continueButtonClick()
                .allFieldsVisible();
        countAfter = SqlUtils.countCredit();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnSuccessNotification() {
        countBefore = SqlUtils.countApprovedPayment();
        open(link);
        elements.buyButtonClick()
                .cardOneInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .successNotificationVisible();
        countAfter = SqlUtils.countApprovedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification() {
        countBefore = SqlUtils.countDeclinedPayment();
        open(link);
        elements.buyButtonClick()
                .cardTwoInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_randomCard() {
        countBefore = SqlUtils.countDeclinedPayment();
        open(link);
        elements.buyButtonClick()
                .fakerCardInput()
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorValidity() {
        countBefore = SqlUtils.countPayment();
        open(link);
        elements.buyButtonClick()
                .cardTwoInput(listCards)
                .invalidMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .monthAndYearError();
        countAfter = SqlUtils.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnSuccessNotification_credit() {
        countBefore = SqlUtils.countApprovedCredit();
        open(link);
        elements.creditBuyButtonClick()
                .cardOneInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .successNotificationVisible();
        countAfter = SqlUtils.countApprovedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_credit() {
        countBefore = SqlUtils.countDeclinedCredit();
        open(link);
        elements.creditBuyButtonClick()
                .cardTwoInput(listCards)
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_randomCard_credit(){
        countBefore = SqlUtils.countDeclinedCredit();
        open(link);
        elements.creditBuyButtonClick()
                .fakerCardInput()
                .validMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorValidity_credit(){
        countBefore = SqlUtils.countCredit();
        open(link);
        elements.creditBuyButtonClick()
                .cardTwoInput(listCards)
                .invalidMonthAndYearInput(monthAndYear)
                .fakerNameAndCvcInput()
                .continueButtonClick()
                .monthAndYearError();
        countAfter = SqlUtils.countCredit();
        assertEquals(countBefore, countAfter);
    }
}
