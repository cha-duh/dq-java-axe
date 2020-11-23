import env.EnvManager;
import env.RunEnvironment;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.axeargs.AxeRunOptions;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;

public class JavaAxeTests {

    @Before
    public void startBrowser() {
        EnvManager.initWebDriver();
    }

    @Deprecated
    public void demo() {
        WebDriver driver = RunEnvironment.getWebDriver();
        driver.get("https://www.blazemeter.com/selenium");
        String homeUrl = driver.findElement(By.cssSelector("div#logo> a#logo_image ")).getAttribute("href");
        Assert.assertEquals(homeUrl, "https://www.blazemeter.com/");
    }

    @Test
    public void axe() {
        long timeBeforeScan = new Date().getTime();

        WebDriver webDriver = RunEnvironment.getWebDriver();
        webDriver.get("https://dequeuniversity.com/demo/mars/");

        AxeRunOptions runOptions = new AxeRunOptions();
        runOptions.setXPath(true);

        AxeBuilder builder = new AxeBuilder().withOptions(runOptions)
                .withTags(Arrays.asList("wcag2a", "wcag412"));

        Results results = builder.analyze(webDriver);

        List<Rule> violations = results.getViolations();
        List<Rule> passes = results.getPasses();

        printUtil(violations);
        printUtil(passes);

        // these are straight up stolen from the example axe integration
        // i don't have any expectations from the demo site, so no assertions to be made
        Assert.assertNotNull(violations.get(0).getId());
        Assert.assertNotEquals("color-contrast", violations.get(0).getId());
        Assert.assertNotNull(results.getViolations().get(0).getTags());
        Assert.assertTrue(results.getViolations().get(0).getTags().contains("wcag2a"));
        Assert.assertTrue(results.getViolations().get(0).getTags().contains("wcag412"));
        Assert.assertEquals(3, violations.size());
        Assert.assertNotNull(results.getViolations().get(0).getNodes());

        long time = new Date().getTime();
        Assert.assertNotEquals(time, timeBeforeScan);
        Assert.assertTrue(time > timeBeforeScan);
        System.out.print(time - timeBeforeScan);
    }

    @After
    public void tearDown() {
        EnvManager.shutDownDriver();
    }

    private void printUtil(List<Rule> items) {
        for (Rule item : items) {
            System.out.println(item.getId());
            System.out.println(item.getHelp());
            System.out.println(item.getHelpUrl());
            System.out.println();
        }
    }
}
