package org.sample;

import static com.codeborne.selenide.WebDriverRunner.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;

public class UiTest {
    private static final String REPORT_BASE_DIR = "target/ui-test";
    private static final String RESOURCE_BASE_DIR = "src/test";
    private static final String CSV_EXTENSION = ".csv";
    private static final String IMAGE_EXTENSION = ".jpg";
    private static final String HTML_EXTENSION = ".html";
    private static final String XLSX_EXTENSION = ".xlsx";
    private static final String HOLD_ON_BROWSER_KEY = "UiTest.hold.on.browser";
    private static final String DATA_SHEET_NAME = "uiTest";
    private Class<?> testClass;

    public UiTest(Class<?> testClass) {
        this.testClass = testClass;
    }

    @BeforeClass
    public static void beforeClass() throws Exception {
        Configuration.reopenBrowserOnFail = true;
        Configuration.browser = WebDriverRunner.CHROME;
        if (WebDriverRunner.CHROME.equalsIgnoreCase(Configuration.browser)) {
            System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
        } else if (WebDriverRunner.FIREFOX.equalsIgnoreCase(Configuration.browser)) {
            System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
        } else {
            throw new IllegalArgumentException("illegal browser. browser=" + Configuration.browser);
        }
        Configuration.holdBrowserOpen = Boolean.valueOf(System.getProperty(HOLD_ON_BROWSER_KEY, "true"));
    }

    @Before
    public void before() throws Exception {
        Configuration.reportsFolder = getReportFolder();
        initReportFolder();
    }

    protected void execute() throws Exception {
        List<Map<String, String>> listMap = new XlsxDataReader().readData(getXlsxPath(), DATA_SHEET_NAME);
        validateData(listMap);
    }

    protected void setUserAgent(String userAgentType) {
        String browser = Configuration.browser;
        String browserVersion = Configuration.browserVersion;
        Platform platForm = Platform.WIN10;
        if ("iPhone".equalsIgnoreCase(userAgentType)) {
            platForm = Platform.IOS;
        } else if ("android".equalsIgnoreCase(userAgentType)) {
            platForm = Platform.ANDROID;
        }
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities(browser, browserVersion,
                platForm);
        WebDriverProvider webDriverProvider = new DefaultWebDriverProvider(getReportFolder(), userAgentType);
        WebDriver driver = webDriverProvider.createDriver(desiredCapabilities);
        WebDriverRunner.setWebDriver(driver);
    }

    protected void takeScreenShot() throws IOException {
        WebDriver driver = getWebDriver();
        ScreenShotSupport.storeHtml(driver, getHtmlPath());
        BufferedImage image = ScreenShotSupport.takeScreenShot(driver);
        ScreenShotSupport.storeImage(image, getImagePath());
    }

    private String getHtmlPath() {
        String htmlPath = null;
        String reportFolder = getReportFolder();
        for (int i = 0; i < 1000; i++) {
            htmlPath = reportFolder + "/PAGE" + String.format("%03d", i) + HTML_EXTENSION;
            if (!new File(htmlPath).exists()) {
                break;
            }
        }
        return htmlPath;
    }

    private String getImagePath() {
        String imagePath = null;
        String reportFolder = getReportFolder();
        for (int i = 0; i < 1000; i++) {
            imagePath = reportFolder + "/PAGE" + String.format("%03d", i) + IMAGE_EXTENSION;
            if (!new File(imagePath).exists()) {
                break;
            }
        }
        return imagePath;
    }

    private void initReportFolder() {
        File reportFolder = new File(getReportFolder());
        if (reportFolder.exists()) {
            for (File f : reportFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(CSV_EXTENSION) || name.endsWith(IMAGE_EXTENSION)
                            || name.endsWith(HTML_EXTENSION);
                }
            })) {
                f.delete();
            }
        } else {
            reportFolder.mkdirs();
        }
    }

    private String getXlsxPath() {
        return RESOURCE_BASE_DIR + "/" + testClass.getName().replaceAll("\\.", "/") + XLSX_EXTENSION;
    }

    protected void storeFile(File file) throws IOException {
        String reportFolder = getReportFolder();
        Files.write(new File(reportFolder + "/" + file.getName()).toPath(), Files.readAllBytes(file.toPath()),
                StandardOpenOption.CREATE);
    }

    private String getReportFolder() {
        try {
            String projectBaseDir = new File("").getCanonicalPath();
            return new File(projectBaseDir + "/" + REPORT_BASE_DIR + "/" + testClass.getName().replaceAll("\\.", "/"))
                    .getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateData(List<Map<String, String>> data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map = data.get(i);
        }
        if(sb.length() != 0) {
            throw new IllegalArgumentException(sb.toString());
        }
    }

}
