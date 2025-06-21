package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999");

        // Заполнение формы
        $("[data-test-id=city] input").setValue("Москва");

        // Генерация даты (текущая дата + 3 дня)
        String deliveryDate = LocalDate.now()
                .plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id=date] input").doubleClick().sendKeys(deliveryDate);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();

        // Проверка успешной отправки
        $("[data-test-id=notification]")
                .shouldHave(Condition.text("Успешно!"))
                .shouldBe(Condition.visible);
    }
}