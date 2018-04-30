package org.sample;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;

public class IPhoneWebDriverProvider implements WebDriverProvider {

    private static final String IPHONE_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1";

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        WebDriver driver = null;
        if (WebDriverRunner.isChrome()) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--user-agent=" + IPHONE_USER_AGENT);
            driver = new ChromeDriver(options);
        } else if (WebDriverRunner.isFirefox()) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("general.useragent.override", IPHONE_USER_AGENT);
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("illegal browser. browser=" + Configuration.browser);
        }
        driver.manage().window().setSize(new Dimension(375, 667));
        return driver;
    }

}
