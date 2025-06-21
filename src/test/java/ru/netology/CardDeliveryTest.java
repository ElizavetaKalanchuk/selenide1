package ru.netology.delivery;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import java.time.Duration;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.timeout = 15000;
        Configuration.pageLoadTimeout = 15000;
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should successful plan meeting")
    void shouldSubmitRequest() {
        // Генерируем тестовые данные
        var user = DataGenerator.Registration.generateUser("ru");
        var deliveryDate = DataGenerator.generateDate(4);

        // Заполняем город
        $("[data-test-id=city] input").setValue(user.getCity().substring(0, 2));
        $$(".menu-item").findBy(text(user.getCity())).click();

        // Очищаем поле даты и заполняем
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(deliveryDate);

        // Заполняем остальные поля
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();

        // Отправляем форму
        $$("button").find(exactText("Забронировать")).click();

        // Ожидаем появления уведомления (более надежный способ)
        $(".notification").shouldBe(visible, Duration.ofSeconds(15));

        // Проверяем текст в уведомлении
        $(".notification .notification__content")
                .shouldHave(text("Встреча успешно забронирована на " + deliveryDate))
                .shouldBe(visible);
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}