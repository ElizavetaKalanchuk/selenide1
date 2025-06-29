package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.nio.channels.Selector;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class CardDeliveryTest {
    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void successfulSendingOfData() { //успешная отправка данных
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно"), Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + planingDate), Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void theCityFieldIsNotFilledIn() { // не заполнено поле город
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='city'].input_invalid .input__sub").findBy(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void dateFieldIsNotFilledIn() { //не заполнено поле дата
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='date'] .input__sub").findBy(text("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void theNameFieldIsNotFilledIn() { // не заполнено поле имя
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='name'].input_invalid .input__sub").findBy(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void thePhoneFieldIsNotFilledIn() { // не заполнено поле телефон
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='phone'].input_invalid .input__sub").findBy(text("Поле обязательно для заполнения")).shouldBe(visible);
    }

    @Test
    void theCheckboxIsNotMarked() { // не проставлен чекбокс
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='agreement'].input_invalid .checkbox__text").findBy(text("Я согласен на обработку моих персональных данных")).shouldBe(visible);
    }

    @Test
    void invalidCity() { // не валидный город
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Малага");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='city'].input_invalid .input__sub").findBy(text("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void invalidDate() { // не верная дата 11.66.1111"
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("11.66.1111");
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='date'] .input__sub").findBy(text("Неверно введена дата")).shouldBe(visible);
    }

    @Test
    void repeatedInvalidDate() { // не верная дата 10.06.2025
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("10.06.2025");
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='date'] .input__sub").shouldHave(text("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void invalidName() { // не валидное имя
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("Ivnov Ivan Ivanovich");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='name'].input_invalid .input__sub").findBy(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void invalidPhone() { // не валидный телефон
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE, planingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+799999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $$("[data-test-id='phone'].input_invalid .input__sub").findBy(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }


    @Test
    void dateSelection() { // выбор даты
        String planingDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999/");

        $("[data-test-id='city'] input").shouldBe(visible, enabled).setValue("Ниж");
        $$(".menu-item").findBy(text("Нижний Новгород")).shouldBe(visible).click();

        $("button").click();
        $("[data-step='1']").click();
        $("[data-step='12']").click();
        $$(".calendar__layout").findBy(text("10")).click();


        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement']").click();
        $$("button").findBy(text("Забронировать")).click();
        $("[data-test-id='notification'] .notification__title").shouldHave(text("Успешно"), Duration.ofSeconds(15)).shouldBe(visible);
        $("[data-test-id='notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на"), Duration.ofSeconds(15)).shouldBe(visible);
    }
}