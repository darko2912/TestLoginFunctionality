package Helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {
    public static String browser = System.getProperty("browser");

    public static void setupDriver() {
        if (browser == null) browser = "chrome";
        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                break;

            default:
                WebDriverManager.chromedriver().setup();
                break;
        }
    }

    public static WebDriver createWebDriver() {
        if (browser == null) browser = "chrome";
        switch (browser) {
            case "firefox":
                return new FirefoxDriver();
            case "edge":
                return new EdgeDriver();
            default:
                return new ChromeDriver();
        }
    }

}