package ui.stepdefinitions;

import com.microsoft.playwright.Page;
import pages.CheckoutStepTwoPage;
import utilities.PlaywrightManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckoutStepTwoSteps {
    private final Page page = PlaywrightManager.getPage();
    private final CheckoutStepTwoPage checkoutStepTwoPage = new CheckoutStepTwoPage(page);

    @When("the finish button is clicked")
    public void clickFinishButton() {
        checkoutStepTwoPage.clickFinish();
    }

    @Then("the order confirmation page should be displayed with the message {string}")
    public void verifyOrderConfirmationPage(String expectedMessage) {
        String confirmationMessageSelector = ".complete-header";
        boolean isDisplayed = page.waitForSelector(confirmationMessageSelector).isVisible();
        assertTrue(isDisplayed, "❌ ERROR: Order confirmation page was NOT displayed.");

        String actualMessage = page.locator(confirmationMessageSelector).textContent();
        assertEquals(expectedMessage, actualMessage,
                "❌ ERROR: Confirmation message does not match.");
    }
}
