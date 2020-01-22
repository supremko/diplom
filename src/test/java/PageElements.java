import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import lombok.val;

import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class PageElements {
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


    public PageElements allFieldsVisible() {
        cardIncorrect.shouldBe(visible);
        monthIncorrect.shouldBe(visible);
        yearIncorrect.shouldBe(visible);
        nameIncorrect.shouldBe(visible);
        cvcIncorrect.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
        return this;
    }

    public PageElements validMonthAndYearInput(Data.MonthAndYear monthAndYear) {
        monthInput.setValue(monthAndYear.getValidMonth());
        yearInput.setValue(monthAndYear.getValidYear());
        return this;
    }

    public PageElements invalidMonthAndYearInput(Data.MonthAndYear monthAndYear) {
        monthInput.setValue(monthAndYear.getInvalidMonth());
        yearInput.setValue(monthAndYear.getInvalidYear());
        return this;
    }

    public PageElements continueButtonClick() {
        continueButton.click();
        return this;
    }

    public PageElements successNotificationVisible() {
        successNotification.waitUntil(visible,10000);
        errorNotification.shouldNotBe(visible);
        return this;
    }

    public PageElements errorNotificationVisible() {
        errorNotification.waitUntil(visible,10000);
        successNotification.shouldNotBe(visible);
        return this;
    }

    public PageElements monthAndYearError() {
        monthError.shouldBe(visible);
        yearError.shouldBe(visible);
        errorNotification.shouldNotBe(visible);
        successNotification.shouldNotBe(visible);
        return this;
    }

    public PageElements fakerNameAndCvcInput() {
        val faker = new Faker(new Locale("ru"));
        nameInput.setValue(faker.name().fullName());
        cvcInput.setValue(faker.numerify("###"));
        return this;
    }

    public PageElements buyButtonClick() {
        buyButton.shouldBe(visible).click();
        return this;
    }

    public PageElements creditBuyButtonClick() {
        creditBuyButton.shouldBe(visible).click();
        return this;
    }

    public PageElements cardOneInput(Data.ListCards listCards) {
        cardInput.waitUntil(visible,1000).setValue(listCards.getCard1());
        return this;
    }

    public PageElements cardTwoInput(Data.ListCards listCards) {
        cardInput.waitUntil(visible,1000).setValue(listCards.getCard2());
        return this;
    }

    public PageElements fakerCardInput() {
        val faker = new Faker();
        cardInput.waitUntil(visible,1000)
                .setValue(faker.numerify("#### #### #### ####"));
        return this;
    }
}
