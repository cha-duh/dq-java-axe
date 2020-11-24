import env.EnvManager;
import env.RunEnvironment;

import java.util.List;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.axeargs.AxeRunOptions;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;

public class JavaAxeTests {

    private WebDriver webDriver;
    private WebDriverWait wait;

    @Before
    public void startBrowser() {

        // update this string for alternate tests
        // String testURL = "localhost:5005"
        String testURL = "https://dequeuniversity.com/demo/mars/";
        EnvManager.initWebDriver();
        this.webDriver = RunEnvironment.getWebDriver();
        this.webDriver.get(testURL);
        this.wait.until(drv -> drv.findElement(By.cssSelector("main")));
    }

    @Deprecated
    public void demo() {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("https://www.blazemeter.com/selenium");
        String homeUrl = driver.findElement(By.cssSelector("div#logo> a#logo_image ")).getAttribute("href");
        Assert.assertEquals(homeUrl, "https://www.blazemeter.com/");
    }

    @Test
    public void mainElementLoaded() {
        WebElement mainElement = wait.until(drv -> drv.findElement(By.cssSelector("main")));
        AxeBuilder builder = new AxeBuilder();
        Results results = builder.analyze(this.webDriver, mainElement);
        Assert.assertNotNull(results);
    }

    @Test
    public void axe() {

        //this.webDriver.get(testURL);

        AxeRunOptions runOptions = new AxeRunOptions();
        runOptions.setXPath(true);

        AxeBuilder builder = new AxeBuilder().withOptions(runOptions);
                //.withTags(Arrays.asList("wcag2a", "wcag412"));

        // run analyze and pull out violations
        Results results = builder.analyze(webDriver);
        List<Rule> violations = results.getViolations();
        int vSize = violations.size();

        // send violations to 'console' per guidelines if there are any
        if (vSize > 0) { printUtil("Violations", violations); }

        // fail test if there are accessibility violations
        Assert.assertEquals(0, vSize);
    }

    @After
    public void tearDown() {
        EnvManager.shutDownDriver();
    }

    private void printUtil(String type, List<Rule> items) {
        String summary = "\nPrinting summary of: ";
        String rawData = "\nPrinting raw data from: ";

        // quick header for type of output
        System.out.print(summary);
        System.out.print(type);
        System.out.println();

        // more legible rules
        for (Rule item : items) {
            System.out.println(item.getId());
            System.out.println(item.getHelp());
            System.out.println(item.getHelpUrl());
            System.out.println();
        }

        // dump raw data with header
        System.out.print(rawData);
        System.out.print(type);
        System.out.println();
        System.out.println(items.toString());
    }
}
