package com.fernfog;

import com.moandjiezana.toml.Toml;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);
    static InputStream tomlFile = null;

    public static void main(String[] args) {
        FirefoxOptions options = new FirefoxOptions();
        URL resource = null;

        try {
            if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                resource = Main.class.getResource("geckodriver_LINUX");
            } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                resource = Main.class.getResource("geckodriver_mac");
            }

            System.setProperty("webdriver.gecko.driver", resource.getPath());

        } catch (NullPointerException e) {
            logger.error(e.toString());
        }

        WebDriver driver = new FirefoxDriver(options);

        driver.get("https://web.telegram.org/k/");
        timer(20, "Увійдіть в свій акаунт");

        try {
            tomlFile = new FileInputStream(args[0]);
        } catch (java.io.FileNotFoundException e) {
            logger.error(e.toString());
        }

        Toml toml = new Toml().read(tomlFile);

        List<Object> dataArray = toml.getList("program.groups");

        for (Object i: dataArray) {
            driver.findElement(By.partialLinkText(i.toString())).click();
            timer(1, "Chat info");
            driver.findElement(By.className("chat-info")).click();

            List<WebElement> elements = driver.findElements(By.cssSelector(".row.no-wrap.row-with-padding.row-clickable.hover-effect.chatlist-chat.chatlist-chat-abitbigger"));

            for (WebElement element : elements) {
                element.click();
                timer(2, "3223");

                try {
                    driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div[4]/div/div[4]")).click();
                    timer(2, "3223");
                } catch (Exception e){
                    logger.error(e.toString());
                }

                try {
                    driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div[4]/div/div[1]/div/div[8]/div[1]/div[1]")).click();
                    driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div/div[2]/div[4]/div/div[1]/div/div[8]/div[1]/div[1]")).sendKeys("хто прочитав - гей", Keys.ENTER);
                    timer(2, "3223");
                } catch (Exception e){
                    logger.error(e.toString());
                }

                WebElement buttonElement = driver.findElement(By.xpath("//button[contains(@class, 'btn-icon') and contains(@class, 'tgico-left') and contains(@class, 'sidebar-close-button')]"));

                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].scrollIntoView(true);", buttonElement);

                executor.executeScript("arguments[0].click();", buttonElement);

            }
        }

        driver.quit();

    }

    public static void timer(int sec, String text) {
        for (int i = sec; i > 0; i--) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e.toString());
            }
            System.out.println("Дія: " + text + " Залишилось секунд: " + i);
        }
    }
}