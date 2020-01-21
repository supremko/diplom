import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTest {
    private String link = "http://localhost:8080";
    private int countBefore;
    private int countAfter;
    PageElements elements = new PageElements();
    Data.MonthAndYear monthAndYear = Data.getMonthAndYear();
    Data.ListCards listCards = Data.getListCards();


    @Test
    void shouldReturnIncorrectFormatError() throws SQLException {
        elements = new PageElements();
        countBefore = SqlUtils.countPayment();
        open(link);
        elements.buyButtonClick();
        elements.continueButtonClick();
        elements.allFieldsVisible();
        countAfter = SqlUtils.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnIncorrectFormatError_credit() throws SQLException {
        countBefore = SqlUtils.countCredit();
        open(link);
        elements.buyButtonClick();
        elements.continueButtonClick();
        elements.allFieldsVisible();
        countAfter = SqlUtils.countCredit();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnSuccessNotification() throws SQLException {
        countBefore = SqlUtils.countApprovedPayment();
        open(link);
        elements.buyButtonClick();
        elements.cardOneInput(listCards);
        elements.validMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.successNotificationVisible();
        countAfter = SqlUtils.countApprovedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification() throws SQLException {
        countBefore = SqlUtils.countDeclinedPayment();
        open(link);
        elements.buyButtonClick();
        elements.cardTwoInput(listCards);
        elements.validMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_randomCard() throws SQLException {
        countBefore = SqlUtils.countDeclinedPayment();
        open(link);
        elements.buyButtonClick();
        elements.fakerCardInput();
        elements.validMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorValidity() throws SQLException {
        countBefore = SqlUtils.countPayment();
        open(link);
        elements.buyButtonClick();
        elements.cardTwoInput(listCards);
        elements.invalidMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.monthAndYearError();
        countAfter = SqlUtils.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnSuccessNotification_credit() throws SQLException {
        countBefore = SqlUtils.countApprovedCredit();
        open(link);
        elements.creditBuyButtonClick();
        elements.cardOneInput(listCards);
        elements.validMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.successNotificationVisible();
        countAfter = SqlUtils.countApprovedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_credit() throws SQLException {
        countBefore = SqlUtils.countDeclinedCredit();
        open(link);
        elements.creditBuyButtonClick();
        elements.cardTwoInput(listCards);
        elements.validMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_randomCard_credit() throws SQLException {
        countBefore = SqlUtils.countDeclinedCredit();
        open(link);
        elements.creditBuyButtonClick();
        elements.fakerCardInput();
        elements.validMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.errorNotificationVisible();
        countAfter = SqlUtils.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorValidity_credit() throws SQLException {
        countBefore = SqlUtils.countCredit();
        open(link);
        elements.creditBuyButtonClick();

        elements.invalidMonthAndYearInput(monthAndYear);
        elements.fakerNameAndCvcInput();
        elements.continueButtonClick();
        elements.monthAndYearError();
        countAfter = SqlUtils.countCredit();
        assertEquals(countBefore, countAfter);
    }
}
