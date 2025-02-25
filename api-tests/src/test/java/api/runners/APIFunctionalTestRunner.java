package api.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "classpath:features/functional/store-api",  // ✅ Functional API feature files
        glue = "api.stepdefinitions",  // ✅ Step definitions location
        plugin = {
                "pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/cucumber.html",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        tags = "@functional and @get"// ✅ Runs only functional tests along with desired tag combination
)
public class APIFunctionalTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true) // ✅ Enables parallel execution of scenarios
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
