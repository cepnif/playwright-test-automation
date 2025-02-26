package api.clients;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import common.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.util.Map;

/**
 * HTTPRequestBuilder is a generic API client for sending HTTP requests using Playwright.
 * It supports GET, POST, PUT, and DELETE methods and handles path and query parameters dynamically.
 */
public class HTTPRequestBuilder {

    private static final Logger logger = LoggerFactory.getLogger(HTTPRequestBuilder.class);
    private final APIRequestContext requestContext;
    private final String baseUrl;

    /**
     * Constructor for HTTPRequestBuilder.
     *
     * @param requestContext Playwright APIRequestContext for making API requests.
     */
    public HTTPRequestBuilder(APIRequestContext requestContext) {
        this.requestContext = requestContext;
        this.baseUrl = ConfigReader.getApiBaseUrl(); // Read Base URL from config.properties
        logger.info("🌐 API Base URL: {}", baseUrl);
    }

    /**
     * Sends an HTTP request based on the specified parameters.
     *
     * @param method      The HTTP method to use (GET, POST, PUT, DELETE).
     * @param endpoint    The API endpoint (e.g., "/store/order/{orderId}").
     * @param headers     The request headers (e.g., {"Authorization": "Bearer token"}).
     * @param queryParams Query parameters to append to the URL (e.g., {"status": "placed"}).
     * @param pathParams  Path parameters for dynamic endpoint replacement (e.g., {"orderId": "10"}).
     * @param requestBody The request body in JSON format (only applicable for POST and PUT requests).
     * @return The APIResponse object containing the server response.
     * @throws IllegalArgumentException If an unsupported HTTP method is specified.
     */
    public APIResponse sendRequest(String method, String endpoint,
                                   Map<String, String> headers,
                                   Map<String, String> queryParams,
                                   Map<String, String> pathParams,
                                   String requestBody) {

        // ✅ Replace path parameters in the endpoint
        if (pathParams != null) {
            for (Map.Entry<String, String> entry : pathParams.entrySet()) {
                endpoint = endpoint.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        // ✅ Append query parameters to the URL
        if (queryParams != null && !queryParams.isEmpty()) {
            StringBuilder queryString = new StringBuilder("?");
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            endpoint += queryString.substring(0, queryString.length() - 1);
        }

        String fullUrl = baseUrl + endpoint; // Construct the full URL

        // ✅ Configure request options
        RequestOptions requestOptions = RequestOptions.create();
        if (headers != null) {
            headers.forEach(requestOptions::setHeader);
        }
        if (requestBody != null && !requestBody.isEmpty()) {
            requestOptions.setData(requestBody);
        }

        logger.info("📩 Sending {} request to: {}", method, fullUrl);
        logger.debug("📩 Headers: {}", headers);
        logger.debug("📩 Query Params: {}", queryParams);
        logger.debug("📩 Request Body: {}", requestBody);

        // ✅ Execute the HTTP request
        APIResponse response;
        switch (method.toUpperCase()) {
            case "GET":
                response = requestContext.get(fullUrl, requestOptions);
                break;
            case "POST":
                response = requestContext.post(fullUrl, requestOptions);
                break;
            case "PUT":
                response = requestContext.put(fullUrl, requestOptions);
                break;
            case "DELETE":
                response = requestContext.delete(fullUrl, requestOptions);
                break;
            default:
                throw new IllegalArgumentException("❌ Invalid HTTP Method: " + method);
        }

        // ✅ Log response details
        logger.info("📨 Response Status: {}", response.status());
        logger.debug("📨 Response Body: {}", response.text());

        return response;
    }

    /**
     * Validates the HTTP response status code.
     *
     * @param response       The APIResponse object.
     * @param expectedStatus The expected HTTP status code.
     * @throws AssertionError If the response status code does not match the expected status.
     */
    public void validateResponse(APIResponse response, int expectedStatus) {
        logger.info("✅ Validating response status: Expected {}, Received {}", expectedStatus, response.status());
        Assert.assertEquals(response.status(), expectedStatus, "❌ Unexpected HTTP status code!");
    }
}
