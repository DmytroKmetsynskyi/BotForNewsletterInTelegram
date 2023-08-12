package com.fernfog;

import com.moandjiezana.toml.Toml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
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

        driver.get("https://web.telegram.org/a/");
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
            timer(5, "TestTestTestTest");
        }

        timer(123123, "aowdkjwlkd");

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