package ui.stepdefinitions;

import com.microsoft.playwright.Page;
import pages.CheckoutCompletePage;
import pages.InventoryPage;
import utilities.PlaywrightManager;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static org.testng.Assert.assertTrue;

public class CheckoutCompleteSteps {
    private final Page page = PlaywrightManager.getPage();
    private final CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(page);
    private final InventoryPage inventoryPage = new InventoryPage(page);

    @When("the back home button is clicked")
    public void clickBackHomeButton() {
        checkoutCompletePage.clickBackHome();
    }
}
