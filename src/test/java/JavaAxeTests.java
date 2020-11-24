import env.EnvManager;
import env.RunEnvironment;

import java.util.List;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

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
        this.wait = new WebDriverWait(webDriver, 20);
    }

    @Test
    public void axeAnalysis() {

        // wait until the main-nav element is loaded or fail
        WebElement mainNav = null;
        try {
            mainNav = wait.until(presenceOfElementLocated(By.cssSelector("#main-nav")));
        } finally {
            Assert.assertNotNull(mainNav);
        }

        // set up for the axe analysis
        AxeRunOptions runOptions = new AxeRunOptions();
        runOptions.setXPath(true);
        AxeBuilder builder = new AxeBuilder().withOptions(runOptions);

        // run analyze and pull out violations
        Results results = builder.analyze(this.webDriver);
        List<Rule> violations = results.getViolations();
        int vSize = violations.size();

        // send violations to 'console' per guidelines if there are any
        if (vSize > 0) { printUtil("Violations", violations); }

        // fail test if there are accessibility violations
        // (no further specificity requested, so >0 violations is considered a failure)
        Assert.assertEquals(0, vSize);
    }

    @After
    public void tearDown() {
        EnvManager.shutDownDriver();
    }

    private void printUtil(String type, List<Rule> items) {
        String summary = "Printing summary of:";
        String rawData = "Printing raw data from:";
        String iSize = Integer.toString(items.size());
        String summaryOut = String.format("%1$s %2$s %3$s%n", summary, iSize, type);
        String rawOut = String.format("%1$s %2$s %3$s%n", rawData, iSize, type);

        // quick header for type of output
        System.out.println(summaryOut);

        // more legible rules
        for (Rule item : items) {
            System.out.println(item.getId());
            System.out.println(item.getHelp());
            System.out.println(item.getHelpUrl());
            System.out.println();
        }

        // dump raw data with header
        System.out.println(rawOut);
        System.out.println(items.toString());
    }
}
