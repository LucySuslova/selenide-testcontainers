package io.github.lucysuslova;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContainerTest extends SetupBase {

    @Test
    public void positiveSearchTest() {
        open("https://google.com/ncr");
        $(By.name("q")).val("Selenide").pressEnter();
        $$("#res .g").shouldHave(sizeGreaterThan(3));
    }

    @Test
    public void negativeSearchTest() {
        open("https://google.com/");
        assertTrue(false);
    }
}
