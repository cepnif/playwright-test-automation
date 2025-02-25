package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;

public class InventoryPage {
    private static final Logger logger = LoggerFactory.getLogger(InventoryPage.class);
    private final Page page;
    private final Locator shoppingCartBadge;

    // ✅ Locators
    private final String item1Image = "#item_4_img_link";
    private final String item2Image = "#item_1_img_link";
    private final String addToCartBtn = "button[id^='add-to-cart']";
    private final String backToProductsBtn = "#back-to-products";
    private final String inventoryList = ".inventory_list";
    private final String cartLink = ".shopping_cart_link";

    public InventoryPage(Page page) {
        this.page = page;
        this.shoppingCartBadge = page.locator(".shopping_cart_badge");
    }

    // ✅ Verify Inventory Page is Visible
    public boolean isInventoryPageVisible() {
        try {
            page.waitForSelector(inventoryList, new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            logger.info("✅ Inventory page is loaded successfully.");
            return true;
        } catch (Exception e) {
            captureScreenshot("debug-inventory-page.png");
            logger.error("❌ ERROR: Inventory page did not load properly. Screenshot: debug-inventory-page.png", e);
            return false;
        }
    }

    // ✅ Add Products to Cart (With Proper Logging)
    public void addProductsToCart() {
        try {
            logger.info("⏩ Adding first product to cart...");
            page.locator(item1Image).click();
            page.waitForSelector(addToCartBtn, new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(3000));
            page.locator(addToCartBtn).click();
            logger.info("✅ First product added successfully.");

            page.locator(backToProductsBtn).click();
            logger.info("🔄 Navigated back to Inventory Page.");

            logger.info("⏩ Adding second product to cart...");
            page.locator(item2Image).click();
            page.waitForSelector(addToCartBtn, new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(3000));
            page.locator(addToCartBtn).click();
            logger.info("✅ Second product added successfully.");

        } catch (Exception e) {
            captureScreenshot("debug-add-to-cart.png");
            logger.error("❌ ERROR: Failed to add items to cart. Screenshot: debug-add-to-cart.png", e);
        }
    }

    // ✅ Open Cart Page
    public void openCart() {
        try {
            logger.info("⏩ Navigating to the Cart Page...");
            page.waitForSelector(cartLink, new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(3000));
            page.locator(cartLink).click();
            logger.info("✅ Successfully opened the Cart Page.");
        } catch (Exception e) {
            captureScreenshot("debug-open-cart.png");
            logger.error("❌ ERROR: Could not open the cart. Screenshot: debug-open-cart.png", e);
        }
    }

    // ✅ Get Cart Item Count
    public int getCartItemCount() {
        try {
            if (shoppingCartBadge.isVisible()) {
                int count = Integer.parseInt(shoppingCartBadge.textContent().trim());
                logger.info("✅ Shopping cart contains {} items.", count);
                return count;
            } else {
                logger.warn("🛒 Shopping cart badge not visible. Cart may be empty.");
                return 0;
            }
        } catch (Exception e) {
            captureScreenshot("debug-cart-count.png");
            logger.error("❌ ERROR: Unable to retrieve cart count. Screenshot: debug-cart-count.png", e);
            return 0;
        }
    }

    // ✅ Utility: Capture Screenshot for Debugging
    private void captureScreenshot(String fileName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
    }
}
