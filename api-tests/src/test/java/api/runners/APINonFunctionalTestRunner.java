package api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "classpath:features/nonfunctional/store-api",  // ✅ Non-Functional API feature files
        glue = "api.stepdefinitions",  // ❌ ❌ ❌ Step definitions not implemented!!!
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/cucumber.html",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        tags = "@non-functional" // ✅ Runs only non-functional tests
)
public class APINonFunctionalTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true) // ✅ Enables parallel execution of scenarios
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
