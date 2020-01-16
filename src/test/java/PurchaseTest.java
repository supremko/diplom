import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.*;
import lombok.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTest {
    private SelenideElement buyButton = $(byText("Купить"));
    private SelenideElement creditBuyButton = $(byText("Купить в кредит"));
    private SelenideElement cardInput = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthInput = $("[placeholder='08']");
    private SelenideElement monthError = $$("[class=input__inner]").findBy(text("Месяц"))
            .$(withText("Неверно указан срок действия карты"));
    private SelenideElement yearInput = $("[placeholder='22']");
    private SelenideElement yearError = $$("[class=input__inner]").findBy(text("Год"))
            .$(withText("Неверно указан срок действия карты"));
    private SelenideElement nameInput = $$("[class=input__inner]").findBy(text("Владелец"))
            .$("[class=input__control]");
    private SelenideElement cvcInput = $("[placeholder='999']");
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement errorNotification = $(withText("Банк отказал в проведении операции"));
    private SelenideElement successNotification = $(withText("Операция одобрена Банком"));
    private SelenideElement cardIncorrect = $$("[class=input__inner]").findBy(text("Номер карты"))
            .$(withText("Неверный формат"));
    private SelenideElement monthIncorrect = $$("[class=input__inner]").findBy(text("Месяц"))
            .$(withText("Неверный формат"));
    private SelenideElement yearIncorrect = $$("[class=input__inner]").findBy(text("Год"))
            .$(withText("Неверный формат"));
    private SelenideElement nameIncorrect = $$("[class=input__inner]").findBy(text("Владелец"))
            .$(withText("Поле обязательно для заполнения"));
    private SelenideElement cvcIncorrect = $$("[class=input__inner]").findBy(text("CVC/CVV"))
            .$(withText("Неверный формат"));
    private String card1 = "4444 4444 4444 4441";
    private String card2 = "4444 4444 4444 4442";
    private String validMonth = "10";
    private String validYear = "22";
    private String invalidMonth = "19";
    private String invalidYear = "30";
    private String link = "http://localhost:8080";
    private int countBefore;
    private int countAfter;


    @Test
    void shouldReturnIncorrectFormatError() throws SQLException {
        countBefore = Sql.countPayment();
        open(link);
        buyButton.shouldBe(visible).click();
        continueButton.click();
        cardIncorrect.shouldBe(visible);
        monthIncorrect.shouldBe(visible);
        yearIncorrect.shouldBe(visible);
        nameIncorrect.shouldBe(visible);
        cvcIncorrect.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnIncorrectFormatError_credit() throws SQLException {
        countBefore = Sql.countCredit();
        open(link);
        buyButton.shouldBe(visible).click();
        continueButton.click();
        cardIncorrect.shouldBe(visible);
        monthIncorrect.shouldBe(visible);
        yearIncorrect.shouldBe(visible);
        nameIncorrect.shouldBe(visible);
        cvcIncorrect.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countCredit();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnSuccessNotification() throws SQLException {
        countBefore = Sql.countApprovedPayment();
        val faker = new Faker(new Locale("ja"));
        open(link);
        buyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000).setValue(card1);
        monthInput.setValue(validMonth);
        yearInput.setValue(validYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        successNotification.waitUntil(visible,10000);
        errorNotification.shouldNotBe(visible);
        countAfter = Sql.countApprovedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification() throws SQLException {
        countBefore = Sql.countDeclinedPayment();
        val faker = new Faker(new Locale("ru"));
        open(link);
        buyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000).setValue(card2);
        monthInput.setValue(validMonth);
        yearInput.setValue(validYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        errorNotification.waitUntil(visible,10000);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_randomCard() throws SQLException {
        countBefore = Sql.countDeclinedPayment();
        val faker = new Faker(new Locale("fr"));
        open(link);
        buyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000)
                .setValue(faker.numerify("#### #### #### ####"));
        monthInput.setValue(validMonth);
        yearInput.setValue(validYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        errorNotification.waitUntil(visible,10000);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countDeclinedPayment();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorValidity() throws SQLException {
        countBefore = Sql.countPayment();
        val faker = new Faker(new Locale("ko"));
        open(link);
        buyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000).setValue(card2);
        monthInput.setValue(invalidMonth);
        yearInput.setValue(invalidYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countPayment();
        assertEquals(countBefore, countAfter);
    }

    @Test
    void shouldReturnSuccessNotification_credit() throws SQLException {
        countBefore = Sql.countApprovedCredit();
        val faker = new Faker(new Locale("ja"));
        open(link);
        creditBuyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000).setValue(card1);
        monthInput.setValue(validMonth);
        yearInput.setValue(validYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        successNotification.waitUntil(visible,10000);
        errorNotification.shouldNotBe(visible);
        countAfter = Sql.countApprovedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_credit() throws SQLException {
        countBefore = Sql.countDeclinedCredit();
        val faker = new Faker(new Locale("ru"));
        open(link);
        creditBuyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000).setValue(card2);
        monthInput.setValue(validMonth);
        yearInput.setValue(validYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        errorNotification.waitUntil(visible,10000);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorNotification_randomCard_credit() throws SQLException {
        countBefore = Sql.countDeclinedCredit();
        val faker = new Faker(new Locale("fr"));
        open(link);
        creditBuyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000)
                .setValue(faker.numerify("#### #### #### ####"));
        monthInput.setValue(validMonth);
        yearInput.setValue(validYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        errorNotification.waitUntil(visible,10000);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countDeclinedCredit();
        assertEquals(countBefore+1, countAfter);
    }

    @Test
    void shouldReturnErrorValidity_credit() throws SQLException {
        countBefore = Sql.countCredit();
        val faker = new Faker(new Locale("ko"));
        open(link);
        creditBuyButton.shouldBe(visible).click();
        cardInput.waitUntil(visible,1000).setValue(card2);
        monthInput.setValue(invalidMonth);
        yearInput.setValue(invalidYear);
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        continueButton.click();
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
        countAfter = Sql.countCredit();
        assertEquals(countBefore, countAfter);
    }
}
