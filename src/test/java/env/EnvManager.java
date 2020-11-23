package env;

import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class EnvManager {

    // initialization of the web driver requires chromedriver
    // @TODO: update this absolute path with your own
    public static void initWebDriver() {

        String DRIVER = SystemUtils.IS_OS_WINDOWS ? "chromedriver.exe" : "chromedriver-mac";
        System.setProperty("webdriver.chrome.driver", DRIVER);
        WebDriver driver = new ChromeDriver();
        RunEnvironment.setWebDriver(driver);
    }

    public static void shutDownDriver() {
        RunEnvironment.getWebDriver().quit();
    }
}
