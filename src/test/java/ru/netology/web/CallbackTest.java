package ru.netology.web;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public  class CallbackTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Мо");
        $$(".menu-item__control").findBy(text("Москва")).click();
        String currentDate = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Виталий Петручик");
        $("[data-test-id=phone] input").setValue("+75436974567");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно!\nВстреча успешно забронирована на " + currentDate));
}
}

