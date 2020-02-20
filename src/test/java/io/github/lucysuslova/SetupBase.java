package io.github.lucysuslova;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static com.codeborne.selenide.Selenide.open;

@Testcontainers
public class SetupBase {

    @Container
    private final BrowserWebDriverContainer browserInContainer =
            new BrowserWebDriverContainer()
                    .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL, new File("build"))
                    .withCapabilities(provideCaps(System.getProperty("selenide.browser")));

    @BeforeEach
    public void setup() {
        RemoteWebDriver driver = browserInContainer.getWebDriver();
        System.out.println(browserInContainer.getContainerId());
        WebDriverRunner.setWebDriver(driver);

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().savePageSource(false).screenshots(true));
        open();
    }

    @AfterEach
    public void teardown() {
        WebDriverRunner.closeWebDriver();
    }

    private DesiredCapabilities provideCaps(String browser) {
        DesiredCapabilities caps;
        switch (browser) {
            case "firefox":
                caps = DesiredCapabilities.firefox();
                break;
            case "chrome":
                caps = DesiredCapabilities.chrome();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + browser);
        }

        return caps;
    }
}
