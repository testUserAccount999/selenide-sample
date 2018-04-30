package org.sample;

import static com.codeborne.selenide.Selenide.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

public class SelectTest {
    @Before
    public void before() throws Exception {
        Configuration.browser = WebDriverRunner.CHROME;
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
    }

    @Test
    public void testInputText() throws Exception {
        // id/selector
        open("http://www.atmarkit.co.jp/fwcr/design/tag/form/input.html");
        $("#name").val("id/selector");
        // name
        open("http://www.atmarkit.co.jp/fwcr/design/tag/form/input.html");
        $(By.name("name")).val("name");
        // xpath
        open("http://www.atmarkit.co.jp/fwcr/design/tag/form/input.html");
        $(By.xpath("//*[@id=\"name\"]")).val("xpath");
    }

    @Test
    public void testTextLink() throws Exception {
        // id/selector
        open("http://www.tagindex.com/html_tag/form/select.html");
        $("#header > nav.path > ol > li:nth-child(3) > a").click();
        // name
        open("http://www.tagindex.com/html_tag/form/select.html");
        $(By.linkText("フォームタグ")).click();
        // xpath
        open("http://www.tagindex.com/html_tag/form/select.html");
        $(By.xpath("//*[@id=\"header\"]/nav[2]/ol/li[2]/a")).click();
    }

    @Test
    public void testCheckBox() throws Exception {
        open("http://www.tagindex.com/html_tag/form/input_checkbox.html");
        // id/selector
        $("#content > section.example > div > figure > dl > dd > p:nth-child(1) > input[type=\"checkbox\"]:nth-child(2)")
                .click();
        // name
        $(By.name("q2")).click();
        // xpath
        $(By.xpath("//*[@id=\"content\"]/section[11]/div/figure/dl/dd/p[3]/input[3]")).click();
    }

    @Test
    public void testSelectBox() throws Exception {
        open("http://www.tagindex.com/html_tag/form/select.html");
        // id/selector
        $("#content > section:nth-child(8) > p:nth-child(4) > select:nth-child(1)").selectOption(3);
        // name
        $(By.name("example2")).selectOptionByValue("サンプル5");
        // xpath
        $(By.xpath("//*[@id=\"content\"]/section[14]/div/figure[1]/dl/dd/p[1]/select")).selectOption("選択肢のサンプル4");
    }

    @Test
    public void testRadio() throws Exception {
        open("http://www.tagindex.com/html_tag/form/input_radio.html");
        //id/selector
        $("#content > section.example > div > figure > dl > dd > p:nth-child(1) > input[type=\"radio\"]:nth-child(3)").setSelected(true);
        // name
        $(By.name("q2")).setSelected(true);
        // xpath
        $(By.xpath("//*[@id=\"content\"]/section[11]/div/figure/dl/dd/p[3]/input[1]")).setSelected(true);
    }

    @Test
    public void testSwitch() throws Exception {
        open("http://orixcredit.jp/member/");
        // id/selector
        $("#homeLogin > form > ul:nth-child(6) > li:nth-child(2) > a").click();
        switchTo().window(1);
        $("#Number").val("test");
        switchTo().window(1).close();
        // xpath
        switchTo().window("ORIX　メンバーズ ネット カウンター");
        $(By.xpath("//*[@id=\"homeLogin\"]/form/ul[1]/li[1]/a")).click();
        switchTo().window(1);
        $("#ID > table > tbody > tr:nth-child(1) > td:nth-child(2) > input:nth-child(2)").val("1234");
    }
}
