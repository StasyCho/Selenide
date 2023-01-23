package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class OrderingCardTest {
    String planningDate = generateDate(3);
    String planningDate2 = generateDate2(2);

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    public String generateDate2(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    @Test
    void shoudOrderingCard() {
        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=name]").setValue("Иванов-Сидоров Иван");
        $("[name=phone]").setValue("+79876543212");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
    @Test
    void shoudNotOrderingCard1() {
        $x("//input[@placeholder='Город']").setValue("Питер");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=name]").setValue("Иванов-Сидоров Иван");
        $("[name=phone]").setValue("+79876543212");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=city] .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }
    @Test
    void shoudNotOrderingCard2() {
        $("[name=name]").setValue("Иванов-Сидоров Иван");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=phone]").setValue("+79876543212");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=city] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
    @Test
    void shoudNotOrderingCard3() {
        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=name]").setValue("Stasy");
        $("[name=phone]").setValue("+79876543212");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shoudNotOrderingCard4() {
        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=phone]").setValue("+79876543212");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shoudNotOrderingCard5() {
        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=name]").setValue("Иванов Иван");
        $("[name=phone]").setValue("+798765432128");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shoudNotOrderingCard6() {
        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=name]").setValue("Иванов Иван");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }
    @Test
    void shoudNotOrderingCard7() {
        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").setValue(planningDate);
        $("[name=name]").setValue("Иванов Иван");
        $("[name=phone]").setValue("+79876543212");
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=agreement]  .checkbox__text").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    void shoudNotOrderingCard8() {
        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").setValue(planningDate2);
        $("[name=name]").setValue("Иванов-Сидоров Иван");
        $("[name=phone]").setValue("+79876543212");
        $(".checkbox__box").click();
        $x("//*[contains(text(), \"Забронировать\")]").click();
        $("[data-test-id=date] .input__sub").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }
}
