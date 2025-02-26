package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the Shopping Cart Page in the SauceDemo UI test automation framework.
 */
public class CartPage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    private final Page page;
    private final Locator checkoutButton;

    /**
     * Constructor for the CartPage.
     *
     * @param page The Playwright page instance used for browser interactions.
     */
    public CartPage(Page page) {
        this.page = page;
        this.checkoutButton = page.locator("#checkout");
    }

    /**
     * Waits for the shopping cart page to load completely.
     * Logs the status of the cart loading process.
     *
     * @throws RuntimeException if the cart page does not load within the timeout period.
     */
    public void waitForCartToLoad() {
        logger.info("🛒 Waiting for the shopping cart to load...");
        try {
            page.waitForSelector("#cart_contents_container",
                    new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            logger.info("✅ Cart loaded successfully.");
        } catch (Exception e) {
            logger.error("❌ ERROR: Cart page did not load in time!", e);
            throw e;
        }
    }

    /**
     * Retrieves the text of a specified item in the shopping cart.
     *
     * @param productName The name of the product whose text needs to be retrieved.
     * @return The product name text if found.
     * @throws RuntimeException if the product text cannot be retrieved.
     */
    public String getItemText(String productName) {
        logger.info("🔍 Fetching item text for: {}", productName);
        try {
            String text = page.locator(String.format("//div[@class='cart_item']//div[text()='%s']", productName)).textContent().trim();
            logger.info("✅ Retrieved item text: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("❌ ERROR: Unable to fetch item text for '{}'", productName, e);
            throw e;
        }
    }

    /**
     * Clicks the 'Checkout' button to proceed to the checkout process.
     * Logs the process and handles potential failures.
     *
     * @throws RuntimeException if the checkout button click fails.
     */
    public void proceedToCheckout() {
        logger.info("🛒 Clicking on 'Checkout' button...");
        try {
            checkoutButton.click();
            logger.info("✅ Checkout process initiated.");
        } catch (Exception e) {
            logger.error("❌ ERROR: Checkout button click failed!", e);
            throw e;
        }
    }
}
