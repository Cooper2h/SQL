package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    public DashboardPage() {
        heading.shouldBe(visible);
    }
}
