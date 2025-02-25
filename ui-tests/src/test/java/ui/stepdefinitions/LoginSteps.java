package ui.stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.LoginPage;
import utilities.PlaywrightManager;

public class LoginSteps {
    private Page page;
    private LoginPage loginPage;

    public LoginSteps() {
        // ✅ Ensure Playwright is initialized before using `getPage()`
        if (PlaywrightManager.getPage() == null) {
            throw new RuntimeException("❌ ERROR: Playwright Page is NULL. Ensure Playwright is started in @BeforeClass.");
        }

        this.page = PlaywrightManager.getPage();
        this.loginPage = new LoginPage(page);
    }

    @Given("the SauceDemo login page is displayed")
    public void the_sauce_demo_login_page_is_displayed() {
        loginPage.navigateToLoginPage();
    }

    @When("the username {string} and password {string} are entered")
    public void the_username_and_password_are_entered(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the login button is clicked")
    public void the_login_button_is_clicked() {
        loginPage.clickLogin();
    }

    @Then("the inventory page should be displayed")
    public void the_inventory_page_should_be_displayed() {
        Assert.assertTrue(page.url().contains("inventory.html"),
                "❌ Login failed! The inventory page was not displayed.");
    }
}
