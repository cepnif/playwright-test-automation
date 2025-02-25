package ui.stepdefinitions;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.InventoryPage;
import utilities.PlaywrightManager;
import static org.testng.Assert.assertEquals;


public class CartSteps {
    private final Page page = PlaywrightManager.getPage();
    private final InventoryPage inventoryPage = new InventoryPage(page);
    private final CartPage cartPage = new CartPage(page);

    @When("the products are added to the cart")
    public void the_product_is_added_to_the_cart() {
        inventoryPage.addProductsToCart();
    }

    @Then("the shopping cart badge should display {int}")
    public void the_shopping_cart_badge_should_display(int expectedCount) {
        int actualCount = inventoryPage.getCartItemCount();
        assertEquals(actualCount, expectedCount, "Cart badge count mismatch!");
    }


    @When("the shopping cart page is opened")
    public void openCartPage() {
        page.locator("#shopping_cart_container").click();
        cartPage.waitForCartToLoad();
    }

    @Then("the cart should contain the product {string}")
    public void verifyCartProduct(String productName) {
        assertEquals(cartPage.getItemText(productName), productName, "❌ The product **" + productName + "** is NOT in the cart.");
    }

    @When("the checkout button is clicked")
    public void clickCheckoutButton() {
        cartPage.proceedToCheckout();
    }
}