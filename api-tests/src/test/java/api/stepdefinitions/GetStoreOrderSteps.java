package api.stepdefinitions;

import api.clients.HTTPRequestBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import common.config.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import java.util.Map;

public class GetStoreOrderSteps {

    private static final Logger logger = LogManager.getLogger(GetStoreOrderSteps.class);

    private Playwright playwright;
    private APIRequestContext requestContext;
    private HTTPRequestBuilder apiClient;
    private APIResponse response;
    private String orderId;

    @Given("the store API is available")
    public void the_store_API_is_available() {
        String baseUrl = ConfigReader.getApiBaseUrl();
        playwright = Playwright.create();
        requestContext = playwright.request().newContext(new APIRequest.NewContextOptions().setBaseURL(baseUrl));

        // Initialize API Client with request context
        apiClient = new HTTPRequestBuilder(requestContext);

        logger.info("✅ API Client initialized with Base URL: {}", baseUrl);
    }

    @Given("an existing order with ID {int}")
    public void an_existing_order_with_ID(int orderId) {
        this.orderId = String.valueOf(orderId);

        // ✅ Prepare request body
        String requestBody = String.format("""
        {
            "id": %d,
            "petId": 1,
            "quantity": 1,
            "shipDate": "2025-02-25T08:04:31.972Z",
            "status": "placed",
            "complete": true
        }
        """, orderId);

        logger.info("🔹 Sending request to create order with ID: {}", orderId);

        // ✅ Send POST request using APIClient
        response = apiClient.sendRequest("POST", "/store/order",
                Map.of("accept", "application/json", "Content-Type", "application/json"),
                null, null, requestBody);

        logger.info("📨 Response Status: {}", response.status());
        logger.debug("📩 Response Body: {}", response.text());

        // ✅ Validate Response
        Assert.assertEquals(response.status(), 200, "❌ Order creation failed!");
        logger.info("✅ Order successfully created with ID: {}", orderId);
    }

    @When("a GET request is sent to retrieve the order")
    public void a_GET_request_is_sent_to_retrieve_the_order() {
        logger.info("🔹 Sending GET request to retrieve order with ID: {}", orderId);

        // ✅ Send GET request using APIClient
        response = apiClient.sendRequest("GET", "/store/order/{orderId}",
                Map.of("accept", "application/json"), null,
                Map.of("orderId", orderId), null);

        logger.info("📨 Response Status: {}", response.status());
        logger.debug("📩 Response Body: {}", response.text());
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedStatusCode) {
        logger.info("🔹 Validating response status code...");
        apiClient.validateResponse(response, expectedStatusCode);
        logger.info("✅ Response status code matches expected: {}", expectedStatusCode);
    }

    @Then("the order details should be displayed")
    public void the_order_details_should_be_displayed() {
        try {
            logger.info("🔹 Validating order details...");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = objectMapper.readValue(response.text(), Map.class);
            Assert.assertEquals(responseBody.get("id"), Integer.parseInt(orderId), "❌ Order details mismatch!");

            logger.info("✅ Order details match for ID: {}", orderId);
        } catch (Exception e) {
            logger.error("❌ Failed to parse or validate JSON response!", e);
            throw new RuntimeException(e);
        }
    }

    @Given("an order ID {string} that does not exist")
    public void an_order_ID_that_does_not_exist(String invalidOrderId) {
        this.orderId = invalidOrderId;
        logger.info("🔹 Testing with invalid order ID: {}", invalidOrderId);
    }

    @Then("an error message {string} should be returned")
    public void an_error_message_should_be_returned(String expectedErrorMessage) {
        try {
            String responseBody = response.text();
            logger.info("🔹 Validating error message...");

            // ✅ Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseJson = objectMapper.readValue(responseBody, Map.class);

            // ✅ Expected Response Structure
            int expectedCode = 1;
            String expectedType = "error";

            // ✅ Assert All Fields
            Assert.assertEquals(responseJson.get("code"), expectedCode, "❌ Mismatched 'code' field!");
            Assert.assertEquals(responseJson.get("type"), expectedType, "❌ Mismatched 'type' field!");
            Assert.assertEquals(responseJson.get("message"), expectedErrorMessage, "❌ Mismatched 'message' field!");

            logger.info("✅ Error response validation passed!");

        } catch (Exception e) {
            logger.error("❌ Failed to parse or validate JSON response!", e);
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() {
        if (playwright != null) {
            playwright.close();
            logger.info("✅ Playwright instance closed.");
        }
    }
}
