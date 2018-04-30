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

public class AndroidWebDriverProvider implements WebDriverProvider {

    private static final String ANDROID_USER_AGENT = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Mobile Safari/537.36";
    private String reportFolderPath;
    private String userAgent;

    public AndroidWebDriverProvider(String reportFolder, String userAgent) {
        this.reportFolderPath = reportFolder;
        this.userAgent = userAgent;
    }

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        WebDriver driver = null;
        if (WebDriverRunner.isChrome()) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--user-agent=" + ANDROID_USER_AGENT);
            driver = new ChromeDriver(options);
        } else if (WebDriverRunner.isFirefox()) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("general.useragent.override", ANDROID_USER_AGENT);
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("illegal browser. browser=" + Configuration.browser);
        }
        driver.manage().window().setSize(new Dimension(360, 640));
        return driver;
    }

}
