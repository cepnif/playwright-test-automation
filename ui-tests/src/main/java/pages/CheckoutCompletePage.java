package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;

/**
 * Represents the Checkout Completion Page in the SauceDemo UI test automation framework.
 */
public class CheckoutCompletePage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutCompletePage.class);
    private final Page page;
    private final Locator backHomeButton;

    /**
     * Constructor for the CheckoutCompletePage.
     *
     * @param page The Playwright page instance used for browser interactions.
     */
    public CheckoutCompletePage(Page page) {
        this.page = page;
        this.backHomeButton = page.locator("#back-to-products");
    }

    /**
     * Clicks the 'Back Home' button to navigate back to the homepage.
     * Implements waiting, logging, and error handling.
     *
     * @throws RuntimeException if the button is not found or unclickable.
     */
    public void clickBackHome() {
        logger.info("🔙 Navigating back to the home page...");
        try {
            // ✅ Wait for the button to be visible before interacting
            page.waitForSelector("#back-to-products", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            logger.info("✅ 'Back Home' button is visible.");

            // ✅ Click on the button to return home
            backHomeButton.click();
            logger.info("✅ Successfully navigated back to the home page.");

        } catch (Exception e) {
            // ✅ Capture screenshot on failure for debugging
            String screenshotPath = "debug-back-home.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            logger.error("❌ ERROR: 'Back Home' button is missing or unclickable. Screenshot saved: {}", screenshotPath, e);
            throw e; // ✅ Re-throw exception for test failure tracking
        }
    }
}
