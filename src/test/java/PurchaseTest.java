import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.*;
import lombok.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

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
    private static String user = "app";
    private static String password = "pass";
    private String card1 = "4444 4444 4444 4441";
    private String card2 = "4444 4444 4444 4442";
    private String validMonth = "10";
    private String validYear = "22";
    private String invalidMonth = "19";
    private String invalidYear = "30";
    private String link = "http://localhost:8080";
    private int countBefore;
    private int countAfter;
    private static String mysql = "jdbc:mysql://localhost:3306/app";
    private static String postgres = "jdbc:postgresql://localhost:5432/app";
    //указать в какой базе данных производится проверка
    private static String database = postgres;


    static int countApprovedPayment() throws SQLException {
        val count = "select count(1) from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='APPROVED';";
        int countApprovedPayment = 0;
        try (
                val conn = DriverManager.getConnection(
                        database, user, password
                );
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countApprovedPayment = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countApprovedPayment = rs.getInt("count");
                    }
                }
            }
        }
        return countApprovedPayment;
    }

    static int countDeclinedPayment() throws SQLException {
        val count = "select count(1) from order_entity o, payment_entity p where o.payment_id=p.transaction_id and p.status='DECLINED';";
        int countDeclinedPayment = 0;
        try (
                val conn = DriverManager.getConnection(
                        database, user, password
                );
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countDeclinedPayment = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countDeclinedPayment = rs.getInt("count");
                    }
                }
            }
        }
        return countDeclinedPayment;
    }

    static int countPayment() throws SQLException {
        val count = "select count(1) from order_entity o, payment_entity p where o.payment_id=p.transaction_id;";
        int countPayment = 0;
        try (
                val conn = DriverManager.getConnection(
                        database, user, password
                );
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countPayment = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countPayment = rs.getInt("count");
                    }
                }
            }
        }
        return countPayment;
    }

    static int countApprovedCredit() throws SQLException {
        val count = "select count(1) from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='APPROVED';";
        int countApprovedCredit = 0;
        try (
                val conn = DriverManager.getConnection(
                        database, user, password
                );
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countApprovedCredit = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countApprovedCredit = rs.getInt("count");
                    }
                }
            }
        }
        return countApprovedCredit;
    }

    static int countDeclinedCredit() throws SQLException {
        val count = "select count(1) from order_entity o, credit_request_entity c where o.payment_id=c.bank_id and c.status='DECLINED';";
        int countDeclinedCredit = 0;
        try (
                val conn = DriverManager.getConnection(
                        database, user, password
                );
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countDeclinedCredit = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countDeclinedCredit = rs.getInt("count");
                    }
                }
            }
        }
        return countDeclinedCredit;
    }

    static int countCredit() throws SQLException {
        val count = "select count(1) from order_entity o, credit_request_entity c where o.payment_id=c.bank_id;";
        int countCredit = 0;
        try (
                val conn = DriverManager.getConnection(
                        database, user, password
                );
                val countStmt = conn.createStatement();
        ) {
            try (val rs = countStmt.executeQuery(count)) {
                while (rs.next()) {
                    if (database == mysql) {
                        countCredit = rs.getInt("count(1)");
                    } else if (database == postgres) {
                        countCredit = rs.getInt("count");
                    }
                }
            }
        }
        return countCredit;
    }

    @Test
    void shouldReturnIncorrectFormatError() throws SQLException, IOException {
        countBefore = countPayment();
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
        countAfter = countPayment();
        if (countAfter != countBefore) {
            throw new IOException("Unexpected addition of new rows to the database" +
                    "|| Неожиданное появление новых строк в БД.");
        }
    }

    @Test
    void shouldReturnIncorrectFormatError_credit() throws SQLException, IOException {
        countBefore = countCredit();
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
        countAfter = countCredit();
        if (countAfter != countBefore) {
            throw new IOException("Unexpected addition of new rows to the database" +
                    "|| Неожиданное появление новых строк в БД.");
        }
    }

    @Test
    void shouldReturnSuccessNotification() throws SQLException, IOException {
        countBefore = countApprovedPayment();
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
        countAfter = countApprovedPayment();
        if (countAfter != countBefore + 1) {
            throw new IOException("An unexpected lack of new rows in the database" +
                    "|| Отсутствуют новые строки в БД.");
        }
    }

    @Test
    void shouldReturnErrorNotification() throws SQLException, IOException {
        countBefore = countDeclinedPayment();
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
        countAfter = countDeclinedPayment();
        if (countAfter != countBefore + 1) {
            throw new IOException("An unexpected lack of new rows in the database" +
                    "|| Отсутствуют новые строки в БД.");
        }
    }

    @Test
    void shouldReturnErrorNotification_randomCard() throws SQLException, IOException {
        countBefore = countDeclinedPayment();
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
        countAfter = countDeclinedPayment();
        if (countAfter != countBefore + 1) {
            throw new IOException("An unexpected lack of new rows in the database" +
                    "|| Отсутствуют новые строки в БД.");
        }
    }

    @Test
    void shouldReturnErrorValidity() throws SQLException, IOException {
        countBefore = countPayment();
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
        countAfter = countPayment();
        if (countAfter != countBefore) {
            throw new IOException("Unexpected addition of new rows to the database" +
                    "|| Неожиданное появление новых строк в БД.");
        }
    }

    @Test
    void shouldReturnSuccessNotification_credit() throws SQLException, IOException {
        countBefore = countApprovedCredit();
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
        countAfter = countApprovedCredit();
        if (countAfter != countBefore + 1) {
            throw new IOException("An unexpected lack of new rows in the database" +
                    "|| Отсутствуют новые строки в БД.");
        }
    }

    @Test
    void shouldReturnErrorNotification_credit() throws SQLException, IOException {
        countBefore = countDeclinedCredit();
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
        countAfter = countDeclinedCredit();
        if (countAfter != countBefore + 1) {
            throw new IOException("An unexpected lack of new rows in the database" +
                    "|| Отсутствуют новые строки в БД.");
        }
    }

    @Test
    void shouldReturnErrorNotification_randomCard_credit() throws SQLException, IOException {
        countBefore = countDeclinedCredit();
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
        countAfter = countDeclinedCredit();
        if (countAfter != countBefore + 1) {
            throw new IOException("An unexpected lack of new rows in the database" +
                    "|| Отсутствуют новые строки в БД.");
        }
    }

    @Test
    void shouldReturnErrorValidity_credit() throws SQLException, IOException {
        countBefore = countCredit();
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
        countAfter = countCredit();
        if (countAfter != countBefore) {
            throw new IOException("Unexpected addition of new rows to the database" +
                    "|| Неожиданное появление новых строк в БД.");
        }
    }
}
