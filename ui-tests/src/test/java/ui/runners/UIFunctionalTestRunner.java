package ui.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utilities.PlaywrightManager;

@CucumberOptions(
        features = "src/test/resources/features/functional",
        glue = {"ui.stepdefinitions"},
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/cucumber.html"
        },
        monochrome = true,
        tags = "@functional"
)
public class UIFunctionalTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    @Parameters("browser")
    public void setUp(@Optional("chromium") String browser) {
        System.out.println("🖥 Running tests on browser: " + browser);
        PlaywrightManager.startPlaywright(browser);

        // ✅ Validate Playwright is initialized
        if (PlaywrightManager.getPage() == null) {
            throw new RuntimeException("❌ ERROR: Playwright was not initialized properly.");
        }
    }

    @AfterClass
    public void tearDown() {
        PlaywrightManager.closePlaywright();
    }

    // ✅ Runs scenarios in parallel
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
