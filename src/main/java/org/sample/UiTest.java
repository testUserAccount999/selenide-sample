package org.sample;

import static com.codeborne.selenide.Selenide.*;
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
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;

public class UiTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UiTest.class);
    private static final String REPORT_BASE_DIR = "target/ui-test";
    private static final String RESOURCE_BASE_DIR = "src/test/resources";
    private static final String CSV_EXTENSION = ".csv";
    private static final String IMAGE_EXTENSION = ".jpg";
    private static final String PNG_EXTENSION = ".png";
    private static final String HTML_EXTENSION = ".html";
    private static final String XLSX_EXTENSION = ".xlsx";
    private static final String PDF_EXTENSION = ".pdf";
    private static final String DATA_SHEET_NAME = "uiTest";
    // USER_AGENT_TYPE
    private static final String USER_AGENT_TYPE_IPHONE = "iPhone";
    private static final String USER_AGENT_TYPE_ANDROID = "android";
    private static final String USER_AGENT_TYPE_PC = "pc";
    // データシートのキー
    private static final String NO_KEY = "No";
    private static final String TYPE_KEY = "type";
    private static final String ID_SELECTOR_KEY = "id/selector";
    private static final String NAME_KEY = "name";
    private static final String XPATH_KEY = "xpath";
    private static final String VALUE_KEY = "value";
    // typeの種類
    private static final String USER_AGENT = "userAgent";
    private static final String OPEN = "open";
    private static final String INSERT_DB = "insertDb";
    private static final String DUMP_DB = "dumpDb";
    private static final String SCREEN_SHOT = "screenShot";
    private static final String JAVASCRIPT = "javaScript";
    private static final String INPUT_TEXT = "inputText";
    private static final String LINK_TEXT = "linkText";
    private static final String CLICK = "click";
    private static final String DOUBLE_CLICK = "doubleClick";
    private static final String SELECT_OPTION_INDEX = "selectOptionIndex";
    private static final String SELECT_OPTION_TEXT = "selectOptionText";
    private static final String SELECT_OPTION_VALUE = "selectOptionValue";
    private static final String SELECT_RADIO = "selectRadio";
    private static final String FILE_UPLOAD = "fileUpload";
    private static final String SELECT_CHECK_BOX = "selectCheckBox";
    private static final String SWITCH_TO_WINDOW_BY_NAME = "switchToWindowByName";
    private static final String SWITCH_TO_WINDOW_BY_INDEX = "switchToWindowByIndex";
    private static final String CLOSE_WINDOW_BY_NAME = "closeWindowByName";
    private static final String CLOSE_WINDOW_BY_INDEX = "closeWindowByIndex";

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
    }

    @Before
    public void before() throws Exception {
        Configuration.reportsFolder = getReportFolder();
        initReportFolder();
    }

    protected void execute() throws Exception {
        List<Map<String, String>> dataListMap = new XlsxDataReader().readData(getXlsxPath(), DATA_SHEET_NAME);
        validateData(dataListMap);
        LOGGER.info("type count=" + dataListMap.size());
        for (Map<String, String> data : dataListMap) {
            String no = data.get(NO_KEY);
            String type = data.get(TYPE_KEY);
            String idSelector = data.get(ID_SELECTOR_KEY);
            String name = data.get(NAME_KEY);
            String xpath = data.get(XPATH_KEY);
            String value = data.get(VALUE_KEY);
            LOGGER.info("No=" + no + ", type=" + type + ", id/selector=" + idSelector + ", name=" + name + ", xpath="
                    + xpath + ", value=" + value);
            switch (type) {
            case USER_AGENT:
                setUserAgent(value);
                break;
            case OPEN:
                open(value);
                break;
            case JAVASCRIPT:
                executeJavaScript(value);
                break;
            case SWITCH_TO_WINDOW_BY_NAME:
                switchTo().window(value);
                break;
            case SWITCH_TO_WINDOW_BY_INDEX:
                switchTo().window(Integer.valueOf(value));
                break;
            case CLOSE_WINDOW_BY_NAME:
                switchTo().window(value).close();
                break;
            case CLOSE_WINDOW_BY_INDEX:
                switchTo().window(Integer.valueOf(value)).close();
                break;
            case SCREEN_SHOT:
                takeScreenShot();
                break;
            case INPUT_TEXT:
                getSelenideElement(idSelector, name, xpath).val(value);
                break;
            case LINK_TEXT:
                SelenideElement selenideElement = null;
                if (!isNullOrEmpty(idSelector)) {
                    selenideElement = $(idSelector);
                } else if (!isNullOrEmpty(name)) {
                    selenideElement = $(By.linkText(name));
                } else if (!isNullOrEmpty(xpath)) {
                    selenideElement = $(xpath);
                }
                selenideElement.click();
                break;
            case CLICK:
            case SELECT_CHECK_BOX:
                getSelenideElement(idSelector, name, xpath).click();
                break;
            case DOUBLE_CLICK:
                getSelenideElement(idSelector, name, xpath).doubleClick();
                break;
            case SELECT_OPTION_INDEX:
                getSelenideElement(idSelector, name, xpath).selectOption(Integer.valueOf(value));
                break;
            case SELECT_OPTION_TEXT:
                getSelenideElement(idSelector, name, xpath).selectOption(value);
                break;
            case SELECT_OPTION_VALUE:
                getSelenideElement(idSelector, name, xpath).selectOptionByValue(value);
                break;
            case SELECT_RADIO:
                getSelenideElement(idSelector, name, xpath).setSelected(Boolean.valueOf(value));
                break;
            case FILE_UPLOAD:
                getSelenideElement(idSelector, name, xpath).uploadFile(new File(getResourceDir(), value));
                break;
            case INSERT_DB:
                // TODO 未実装
                break;
            case DUMP_DB:
                // TODO 未実装
                break;
            default:
                throw new UnsupportedOperationException("想定しないtypeです。type=" + type);
            }
        }
    }

    protected void setUserAgent(String userAgentType) {
        String browser = Configuration.browser;
        String browserVersion = Configuration.browserVersion;
        Platform platForm = Platform.WIN10;
        if (USER_AGENT_TYPE_IPHONE.equalsIgnoreCase(userAgentType)) {
            platForm = Platform.IOS;
        } else if (USER_AGENT_TYPE_ANDROID.equalsIgnoreCase(userAgentType)) {
            platForm = Platform.ANDROID;
        }
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities(browser, browserVersion, platForm);
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
                            || name.endsWith(HTML_EXTENSION) || name.endsWith(PDF_EXTENSION)
                            || name.endsWith(PNG_EXTENSION);
                }
            })) {
                f.delete();
            }
        } else {
            reportFolder.mkdirs();
        }
    }

    private File getResourceDir() {
        return new File(getXlsxPath()).getParentFile();
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
            String type = map.get(TYPE_KEY);
            String value = map.get(VALUE_KEY);
            if (i == 0) {
                if (!USER_AGENT.equalsIgnoreCase(type)) {
                    sb.append("userAgentの設定が先頭にありません。").append(System.lineSeparator());
                    continue;
                }
                if (!USER_AGENT_TYPE_PC.equalsIgnoreCase(value) && !USER_AGENT_TYPE_ANDROID.equalsIgnoreCase(value)
                        && !USER_AGENT_TYPE_IPHONE.equalsIgnoreCase(value)) {
                    sb.append("userAgentの設定が不正です。valueにはPC、iPhone、Androidのいずれかを指定してください。")
                            .append(System.lineSeparator());
                }
                continue;
            }
            String no = map.get(NO_KEY);
            String idSelector = map.get(ID_SELECTOR_KEY);
            String name = map.get(NAME_KEY);
            String xpath = map.get(XPATH_KEY);
            switch (type) {
            case OPEN:
            case JAVASCRIPT:
            case SWITCH_TO_WINDOW_BY_NAME:
            case CLOSE_WINDOW_BY_NAME:
                if (isNullOrEmpty(value)) {
                    sb.append(type).append("にvalueが設定されていません。No=").append(no).append(System.lineSeparator());
                }
                break;
            case SWITCH_TO_WINDOW_BY_INDEX:
            case CLOSE_WINDOW_BY_INDEX:
                if (isNullOrEmpty(value)) {
                    sb.append(type).append("にvalueが設定されていません。No=").append(no).append(System.lineSeparator());
                    break;
                }
                if (!value.matches("^\\d+$")) {
                    sb.append(type).append("のvalueには数字を設定してください。No=").append(no).append(System.lineSeparator());
                }
                break;
            case INPUT_TEXT:
            case SELECT_OPTION_INDEX:
            case SELECT_OPTION_TEXT:
            case SELECT_OPTION_VALUE:
            case SELECT_CHECK_BOX:
            case FILE_UPLOAD:
                if (isNullOrEmpty(idSelector) && isNullOrEmpty(name) && isNullOrEmpty(xpath)) {
                    sb.append(type).append("にはid/selector, name, xpathのいずれかを設定してください。No=").append(no)
                            .append(System.lineSeparator());
                }
                if (isNullOrEmpty(value)) {
                    sb.append(type).append("にvalueが設定されていません。No=").append(no).append(System.lineSeparator());
                }
                if (SELECT_CHECK_BOX.equalsIgnoreCase(type) && !"true".equalsIgnoreCase(value)
                        && !"false".equalsIgnoreCase(value)) {
                    sb.append(type).append("のvalueにはture,falseを設定してください。value=").append(value).append(", No=")
                            .append(no).append(System.lineSeparator());
                }
                break;
            case LINK_TEXT:
            case CLICK:
            case DOUBLE_CLICK:
            case SELECT_RADIO:
                if (isNullOrEmpty(idSelector) && isNullOrEmpty(name) && isNullOrEmpty(xpath)) {
                    sb.append(type).append("にはid/selector, name, xpathのいずれかを設定してください。No=").append(no)
                            .append(System.lineSeparator());
                }
                break;
            case INSERT_DB:
            case DUMP_DB:
            case SCREEN_SHOT:
                // 精査なし
                break;
            default:
                sb.append("typeに想定しない設定が指定してあります。No=").append(no).append(", type=").append(type)
                        .append(System.lineSeparator());
                break;
            }
        }
        if (sb.length() != 0) {
            throw new IllegalArgumentException(sb.toString());
        }
    }

    private SelenideElement getSelenideElement(String idSelector, String name, String xpath) {
        SelenideElement selenideElement = null;
        if (!isNullOrEmpty(idSelector)) {
            selenideElement = $(idSelector);
        } else if (!isNullOrEmpty(name)) {
            selenideElement = $(By.name(name));
        } else if (!isNullOrEmpty(xpath)) {
            selenideElement = $(By.xpath(xpath));
        }
        return selenideElement;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.length() == 0;
    }
}
