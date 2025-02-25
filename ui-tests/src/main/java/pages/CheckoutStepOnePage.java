package pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Paths;

public class CheckoutStepOnePage {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutStepOnePage.class);
    private final Page page;
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator postalCodeField;
    private final Locator continueButton;

    public CheckoutStepOnePage(Page page) {
        this.page = page;
        this.firstNameField = page.locator("#first-name");
        this.lastNameField = page.locator("#last-name");
        this.postalCodeField = page.locator("#postal-code");
        this.continueButton = page.locator("#continue");
    }

    // ✅ Check if Checkout Page is Displayed
    public boolean isCheckoutPageDisplayed() {
        String checkoutPageSelector = "#checkout_info_container";
        logger.info("🔍 Verifying if Checkout Step One page is displayed...");

        try {
            page.waitForSelector(checkoutPageSelector, new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            boolean isVisible = page.locator(checkoutPageSelector).isVisible();
            logger.info("✅ Checkout Step One page is visible.");
            return isVisible;
        } catch (Exception e) {
            captureScreenshot("debug-checkout-page.png");
            logger.error("❌ ERROR: Checkout page did not load properly.", e);
            return false;
        }
    }

    // ✅ Enter Customer Details
    public void enterCustomerDetails(String firstName, String lastName, String postalCode) {
        logger.info("✍️ Entering customer details: {} {} - {}", firstName, lastName, postalCode);
        try {
            page.waitForSelector("#first-name", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));

            firstNameField.fill(firstName);
            lastNameField.fill(lastName);
            postalCodeField.fill(postalCode);

            logger.info("✅ Successfully entered customer details.");
        } catch (Exception e) {
            captureScreenshot("debug-checkout-step-one.png");
            logger.error("❌ ERROR: Unable to enter checkout details. Screenshot saved: debug-checkout-step-one.png", e);
            throw e; // ✅ Propagate the error to fail the test
        }
    }

    // ✅ Click Continue Button
    public void clickContinue() {
        logger.info("⏩ Clicking 'Continue' button on Checkout Step One...");
        try {
            page.waitForSelector("#continue", new Page.WaitForSelectorOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(5000));
            continueButton.click();
            logger.info("✅ Clicked 'Continue' button successfully.");
        } catch (Exception e) {
            captureScreenshot("debug-checkout-continue.png");
            logger.error("❌ ERROR: 'Continue' button is missing or not clickable. Screenshot: debug-checkout-continue.png", e);
            throw e; // ✅ Ensure test failure if step fails
        }
    }

    // ✅ Utility: Capture Screenshot for Debugging
    private void captureScreenshot(String fileName) {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
    }
}
