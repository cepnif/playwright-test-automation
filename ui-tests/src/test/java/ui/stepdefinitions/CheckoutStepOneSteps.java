package ui.stepdefinitions;

import com.microsoft.playwright.Page;
import pages.CheckoutStepOnePage;
import utilities.PlaywrightManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertTrue;

public class CheckoutStepOneSteps {
    private final Page page = PlaywrightManager.getPage();
    private final CheckoutStepOnePage checkoutStepOnePage = new CheckoutStepOnePage(page);

    @Then("the checkout information page should be displayed")
    public void verifyCheckoutPage() {
        boolean isDisplayed = checkoutStepOnePage.isCheckoutPageDisplayed();
        assertTrue(isDisplayed, "❌ ERROR: Checkout information page was NOT displayed. Check debug-checkout-page.png.");
    }

    @When("the first name {string}, last name {string}, and postal code {string} are entered")
    public void enterShippingDetails(String firstName, String lastName, String postalCode) {
        checkoutStepOnePage.enterCustomerDetails(firstName, lastName, postalCode);
    }

    @When("the continue button is clicked")
    public void clickContinueButton() {
        checkoutStepOnePage.clickContinue();
    }

    @Then("the order summary page should be displayed")
    public void verifyOrderSummaryPage() {
        String orderSummarySelector = "#checkout_summary_container";
        boolean isDisplayed = page.waitForSelector(orderSummarySelector).isVisible();
        assertTrue(isDisplayed, "❌ ERROR: Order summary page was NOT displayed.");
    }
}
