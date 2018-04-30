package org.sample;

import static com.codeborne.selenide.Selenide.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ScreenShotSupport {
    private static final String PASTING_MILLISECONDS = "150";
    private static final String PASTING_MILLISECONDS_KEY = "ScreenShotSupport.milliseconds";
    public static BufferedImage takeScreenShot(WebDriver driver) {
        int pastingMillseconds = Integer.valueOf(System.getProperty(PASTING_MILLISECONDS_KEY, PASTING_MILLISECONDS));
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(pastingMillseconds))
                .takeScreenshot(driver);
        executeJavaScript("window.scrollTo(0,0)");
        return screenshot.getImage();
    }
    public static void storeHtml(WebDriver driver, String storePath) throws IOException {
        Files.write(new File(storePath).toPath(), driver.getPageSource().getBytes(), StandardOpenOption.CREATE);
    }
    public static void storeImage(BufferedImage image, String storePath) throws IOException {
        ImageIO.write(image, "jpg", new File(storePath));
    }

}
