package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;

/**
 * Represents the Checkout Step Two Page in the SauceDemo UI test automation framework.
 */
public class CheckoutStepTwoPage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutStepTwoPage.class);
    private final Page page;
    private final Locator finishButton;

    /**
     * Constructor for CheckoutStepTwoPage.
     *
     * @param page The Playwright page instance used for browser interactions.
     */
    public CheckoutStepTwoPage(Page page) {
        this.page = page;
        this.finishButton = page.locator("#finish");
    }

    /**
     * Clicks the 'Finish' button to complete the checkout process.
     *
     * @throws RuntimeException if the button is not found or not clickable.
     */
    public void clickFinish() {
        logger.info("⏩ Clicking 'Finish' button on Checkout Step Two...");

        try {
            // ✅ Wait for 'Finish' button to be visible
            page.waitForSelector("#finish", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            // ✅ Click on the 'Finish' button
            finishButton.click();
            logger.info("✅ Successfully clicked 'Finish' button.");
        } catch (Exception e) {
            captureScreenshot("debug-checkout-finish.png");
            logger.error("❌ ERROR: 'Finish' button is missing or not clickable. Screenshot saved: debug-checkout-finish.png", e);
            throw e; // ✅ Propagate error to fail the test
        }
    }

    /**
     * Captures a screenshot for debugging purposes.
     *
     * @param fileName The name of the file where the screenshot will be saved.
     */
    private void captureScreenshot(String fileName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
    }
}
