package org.sample;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;

public class DefaultWebDriverProvider implements WebDriverProvider {

    private static final String ANDROID_USER_AGENT = "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Mobile Safari/537.36";
    private static final String IPHONE_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1";
    private String reportFolderPath;
    private String userAgentType;

    public DefaultWebDriverProvider(String reportFolderPath, String userAgentType) {
        this.reportFolderPath = reportFolderPath;
        this.userAgentType = userAgentType;
    }

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        String userAgent = null;
        if ("android".equalsIgnoreCase(userAgentType)) {
            userAgent = ANDROID_USER_AGENT;
        } else if ("iphone".equalsIgnoreCase(userAgentType)) {
            userAgent = IPHONE_USER_AGENT;
        }
        WebDriver driver = null;
        if (WebDriverRunner.isChrome()) {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<>();
            //prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("download.default_directory", reportFolderPath);
            options.setExperimentalOption("prefs", prefs);
            if (userAgent != null) {
                options.addArguments("--user-agent=" + userAgent);
            }
            driver = new ChromeDriver(options);
        } else if (WebDriverRunner.isFirefox()) {
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("browser.download.dir", reportFolderPath);
            options.addPreference("browser.download.folderList", 2);
            options.addPreference("browser.download.useDownloadDir", true);
            options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf;");
            options.addPreference("pdfjs.disabled", true);
            if (userAgent != null) {
                options.addPreference("general.useragent.override", userAgent);
            }
            driver = new FirefoxDriver(options);
        }
        if ("android".equalsIgnoreCase(userAgentType)) {
            driver.manage().window().setSize(new Dimension(360, 640));
        } else if ("iphone".equalsIgnoreCase(userAgentType)) {
            driver.manage().window().setSize(new Dimension(375, 667));
        } else {
            driver.manage().window().maximize();
        }
        return driver;
    }

}
