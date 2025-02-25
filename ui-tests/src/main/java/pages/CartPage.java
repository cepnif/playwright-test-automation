package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartPage {
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);
    private final Page page;
    private final Locator checkoutButton;

    public CartPage(Page page) {
        this.page = page;
        this.checkoutButton = page.locator("#checkout");
    }

    // ✅ Wait for cart to load with logging
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

    // ✅ Retrieve product text from cart
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

    // ✅ Proceed to checkout with logging
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
